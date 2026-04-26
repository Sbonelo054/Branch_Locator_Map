package com.sa.branchlocatormap.di

import com.sa.branchlocatormap.data.dataSource.BankBranchLocalDataSource
import org.koin.dsl.module

/**
 * Koin module responsible for providing data source dependencies.
 *
 * This module defines how local data sources are created and injected
 * into the application.
 */
val dataSourceModule = module {

    /**
     * Provides a singleton instance of [BankBranchLocalDataSource].
     *
     * Behavior:
     * - Creates the local data source that contains hardcoded branch data
     * - Acts as a fake or temporary data provider (mock data)
     *
     * Scope:
     * - `single` ensures one shared instance across the entire app
     *
     * Usage:
     * - Injected into repositories (e.g., BankRepository)
     */
    single {
        BankBranchLocalDataSource()
    }
}