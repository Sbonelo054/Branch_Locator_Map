package com.sa.branchlocatormap.di

import com.sa.branchlocatormap.domain.IFavouritesRepository
import com.sa.branchlocatormap.data.BankRepository
import com.sa.branchlocatormap.data.FavouritesRepository
import com.sa.branchlocatormap.domain.IBankRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<IBankRepository> { BankRepository() }

    single<IFavouritesRepository> { FavouritesRepository(get()) }
}