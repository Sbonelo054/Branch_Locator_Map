package com.sa.branchlocatormap.di

import com.sa.branchlocatormap.presentation.viewModel.FavouriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { FavouriteViewModel() }
}