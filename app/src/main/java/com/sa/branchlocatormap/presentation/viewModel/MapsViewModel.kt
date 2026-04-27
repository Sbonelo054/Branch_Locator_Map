package com.sa.branchlocatormap.presentation.viewModel

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.sa.branchlocatormap.domain.model.BankBranchDetail
import com.sa.branchlocatormap.domain.repository.IBankRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel responsible for map-related business logic.
 *
 * Responsibilities:
 * - Holding search query state
 * - Providing full list of branches
 * - Filtering branches based on search input
 * - Computing nearby branches based on user location
 */
class MapsViewModel(
    repository: IBankRepository
) : ViewModel() {

    /**
     * Current search query entered by the user.
     */
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    /**
     * Full list of bank branches from repository.
     *
     * Converted into StateFlow to allow reactive combinations.
     */
    private val _branches = repository.branches
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    /**
     * Filtered list of branches based on search query.
     *
     * Behavior:
     * - If query is blank → returns empty list
     * - Otherwise filters branches by name (case-insensitive)
     */
    val filteredBranches: StateFlow<List<BankBranchDetail>> =
        combine(_branches, _searchQuery) { branches, query ->

            if (query.isBlank()) {
                emptyList()
            } else {
                branches.filter { branch ->
                    branch.name.contains(query, ignoreCase = true)
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    /**
     * Current user location.
     */
    private val _currentLocation = MutableStateFlow<LatLng?>(null)
    val currentLocation = _currentLocation.asStateFlow()

    /**
     * Updates user location (used for nearby branch calculation).
     */
    fun updateLocation(location: LatLng) {
        _currentLocation.value = location
    }

    /**
     * List of the 3 nearest branches to the user's current location.
     *
     * Behavior:
     * - Calculates distance using Android Location API
     * - Sorts branches by distance
     * - Returns top 3 closest branches
     *
     * If location is null → returns empty list
     */
    val nearbyBranches: StateFlow<List<BankBranchDetail>> =
        combine(_branches, _currentLocation) { branches, location ->

            if (location == null) return@combine emptyList()

            branches.map { branch ->

                val results = FloatArray(1)

                Location.distanceBetween(
                    location.latitude,
                    location.longitude,
                    branch.latitude,
                    branch.longitude,
                    results
                )

                branch to results[0]
            }
                .sortedBy { it.second }
                .map { it.first }
                .take(3)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    /**
     * Updates the search query.
     */
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
}