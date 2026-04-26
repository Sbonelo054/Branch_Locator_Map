package com.sa.branchlocatormap.di

import com.sa.branchlocatormap.presentation.viewModel.BranchSharedViewModel
import com.sa.branchlocatormap.presentation.viewModel.FavouritesViewModel
import com.sa.branchlocatormap.presentation.viewModel.MapsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module responsible for providing ViewModel instances.
 *
 * ViewModels act as the presentation layer in MVVM architecture,
 * connecting the UI to the domain/repository layer.
 */
val viewModelModule = module {

    /**
     * Provides [MapsViewModel] with its required dependency.
     *
     * Dependency chain:
     * MapsViewModel → IBankRepository
     *
     * Scope:
     * - `viewModel {}` ensures lifecycle-aware instances
     * - Automatically cleared when the UI lifecycle ends
     */
    viewModel {
        MapsViewModel(get())
    }

    /**
     * Provides [FavouritesViewModel] with its required dependency.
     *
     * Dependency chain:
     * FavouritesViewModel → IFavouritesRepository
     */
    viewModel {
        FavouritesViewModel(get())
    }

    /**
     * Provides a shared instance of [BranchSharedViewModel].
     * Use case:
     * - Shared communication between screens
     * - Cross-feature state sharing
     */
    single {
        BranchSharedViewModel()
    }
}