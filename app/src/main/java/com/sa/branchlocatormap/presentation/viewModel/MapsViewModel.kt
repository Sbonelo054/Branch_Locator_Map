package com.sa.branchlocatormap.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sa.branchlocatormap.domain.BankBranchDetail
import com.sa.branchlocatormap.domain.IBankRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MapsViewModel(
    private val repository: IBankRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _branches = repository.branches
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

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

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
}