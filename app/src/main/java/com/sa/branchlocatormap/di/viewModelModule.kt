package com.sa.branchlocatormap.di

import com.sa.branchlocatormap.presentation.viewModel.BranchSharedViewModel
import com.sa.branchlocatormap.presentation.viewModel.FavouritesViewModel
import com.sa.branchlocatormap.presentation.viewModel.MapsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MapsViewModel(get())
    }

    viewModel {
        FavouritesViewModel(get())
    }

    single {
        BranchSharedViewModel()
    }
}