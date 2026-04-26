package com.sa.branchlocatormap.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sa.branchlocatormap.domain.BankBranchDetail
import com.sa.branchlocatormap.domain.IFavouritesRepository
import com.sa.branchlocatormap.presentation.uiState.FavouritesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing favourite bank branches UI state.
 *
 * It acts as a bridge between:
 * - IFavouritesRepository (data layer)
 * - UI (Compose / XML screens)
 *
 * Responsibilities:
 * - Observing favourite branches in real time
 * - Adding a branch to favourites
 * - Removing a branch from favourites
 */
class FavouritesViewModel(
    private val repository: IFavouritesRepository
) : ViewModel() {

    /**
     * Internal mutable UI state.
     */
    private val _favouriteUiState = MutableStateFlow(FavouritesUiState())

    /**
     * Publicly exposed immutable UI state.
     */
    val favouriteUiState: StateFlow<FavouritesUiState> = _favouriteUiState

    init {
        observeFavourites()
    }

    /**
     * Observes favourite branches from the repository and updates UI state.
     *
     * This runs as a long-lived coroutine tied to the ViewModel scope.
     *
     * Behavior:
     * - Collects Flow from repository
     * - Updates UI state whenever data changes
     */
    private fun observeFavourites() {
        viewModelScope.launch {
            repository.getFavouriteBranches()
                .collect { list ->
                    _favouriteUiState.update {
                        it.copy(favourites = list)
                    }
                }
        }
    }

    /**
     * Adds a branch to favourites.
     *
     * @param branch The branch to be saved.
     */
    fun addFavourite(branch: BankBranchDetail) {
        viewModelScope.launch {
            repository.saveFavouriteBranch(branch)
        }
    }

    /**
     * Removes a branch from favourites.
     *
     * @param branch The branch to be removed .
     */
    fun deleteFavourite(branch: BankBranchDetail) {
        viewModelScope.launch {
            repository.deleteFavourite(branch)
        }
    }
}