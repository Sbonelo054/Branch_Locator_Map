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

class FavouritesViewModel(
    private val repository: IFavouritesRepository
) : ViewModel() {

    private val _favouriteUiState = MutableStateFlow(FavouritesUiState())
    val favouriteUiState: StateFlow<FavouritesUiState> = _favouriteUiState

    init {
        observeFavourites()
    }

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

    fun addFavourite(branch: BankBranchDetail) {
        viewModelScope.launch {
            repository.saveFavouriteBranch(branch)
        }
    }

    fun deleteFavourite(branch: BankBranchDetail) {
        viewModelScope.launch {
            repository.deleteFavourite(branch)
        }
    }
}