package com.sa.branchlocatormap.di

import com.sa.branchlocatormap.domain.repository.IFavouritesRepository
import com.sa.branchlocatormap.data.repository.BankRepository
import com.sa.branchlocatormap.data.repository.FavouritesRepository
import com.sa.branchlocatormap.domain.repository.IBankRepository
import org.koin.dsl.module

/**
 * Koin module responsible for providing repository implementations.
 *
 * Repositories act as the bridge between data sources (local/remote/database)
 * and the rest of the application (e.g., ViewModels).
 */
val repositoryModule = module {

    /**
     * Provides a singleton implementation of [IBankRepository].
     *
     * Behavior:
     * - Injects [BankBranchLocalDataSource] using `get()`
     * - Supplies it to [BankRepository]
     *
     * Dependency chain:
     * IBankRepository → BankRepository → BankBranchLocalDataSource
     */
    single<IBankRepository> {
        BankRepository(get())
    }

    /**
     * Provides a singleton implementation of [IFavouritesRepository].
     *
     * Behavior:
     * - Injects [BranchLocatorDao] using `get()`
     * - Supplies it to [FavouritesRepository]
     *
     * Dependency chain:
     * IFavouritesRepository → FavouritesRepository → BranchLocatorDao → Room Database
     */
    single<IFavouritesRepository> {
        FavouritesRepository(get())
    }
}