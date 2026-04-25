package com.sa.branchlocatormap.presentation.uiState

import com.sa.branchlocatormap.domain.BankBranchDetail

data class FavouritesUiState(
    val favourites: List<BankBranchDetail> = emptyList(),
    val isLoading: Boolean = false
)