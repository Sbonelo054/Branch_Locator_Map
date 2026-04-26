package com.sa.branchlocatormap.presentation.uiState

import com.sa.branchlocatormap.domain.BankBranchDetail

/**
 * UI state holder for the Favourites screen.
 *
 * This class represents all possible states the UI can be in
 * while displaying favourite bank branches.
 *
 * It is typically exposed from a ViewModel as a StateFlow
 * and observed by the UI layer (e.g. Compose or XML).
 */
data class FavouritesUiState(

    /**
     * List of favourite bank branches to display.
     *
     * Default is an empty list when no data is available yet.
     */
    val favourites: List<BankBranchDetail> = emptyList(),

    /**
     * Indicates whether the UI is currently loading data.
     *
     * Used to show loading indicators such as:
     * - Progress bars
     * - Shimmer effects
     */
    val isLoading: Boolean = false
)