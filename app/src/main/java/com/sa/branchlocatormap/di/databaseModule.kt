package com.sa.branchlocatormap.di

import com.sa.branchlocatormap.data.BranchLocatorDao
import com.sa.branchlocatormap.data.BranchLocatorDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Koin module responsible for providing database-related dependencies.
 *
 * This module defines how the Room database and its DAO are created
 * and injected across the application.
 */
val databaseModule = module {

    /**
     * Provides a singleton instance of [BranchLocatorDatabase].
     *
     * Behavior:
     * - Calls the database singleton `getInstance()` method
     * - Uses the Android application context via `androidApplication()`
     *
     * Scope:
     * - `single` ensures only one instance exists throughout the app lifecycle
     */
    single {
        BranchLocatorDatabase.getInstance(androidApplication())
    }

    /**
     * Provides a singleton instance of [BranchLocatorDao].
     *
     * Behavior:
     * - Retrieves the database instance from Koin
     * - Calls `branchLocatorDao()` to get the DAO
     *
     * Dependency chain:
     * DAO → depends on → Database
     */
    single<BranchLocatorDao> {
        get<BranchLocatorDatabase>().branchLocatorDao()
    }
}