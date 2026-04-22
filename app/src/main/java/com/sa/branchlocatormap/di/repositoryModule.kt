package com.sa.branchlocatormap.di

import com.sa.branchlocatormap.data.IFavouritesRepository
import com.sa.branchlocatormap.domain.FavouritesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<IFavouritesRepository> { FavouritesRepository() }
}