package com.sa.branchlocatormap.presentation.viewModel

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.sa.branchlocatormap.domain.BankBranchDetail
import com.sa.branchlocatormap.domain.IBankRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MapsViewModel(repository: IBankRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _branches = repository.branches
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

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

    private val _currentLocation = MutableStateFlow<LatLng?>(null)

    fun updateLocation(location: LatLng) {
        _currentLocation.value = location
    }

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

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
}