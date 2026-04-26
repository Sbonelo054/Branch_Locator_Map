package com.sa.branchlocatormap

import android.app.Application
import com.sa.branchlocatormap.di.dataSourceModule
import com.sa.branchlocatormap.di.databaseModule
import com.sa.branchlocatormap.di.repositoryModule
import com.sa.branchlocatormap.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
/**
 * Custom Application class for the Branch Locator app.
 *
 * This class is responsible for initializing global dependencies
 * and application-wide configurations before any Activity is created.
 *
 * Responsibilities:
 * - Initializes Koin Dependency Injection framework
 * - Provides application context to DI graph
 * - Registers all app modules (repositories, viewModels, database, data sources)
 */
class BranchLocatorApplication : Application() {

    /**
     * Called when the application is starting.
     *
     * This is the first entry point of the app lifecycle.
     * Koin is initialized here to ensure dependencies are available globally.
     */
    override fun onCreate() {
        super.onCreate()
        /**
         * Starts Koin dependency injection framework.
         */
        startKoin {
            /**
             * Enables logging for Koin dependency resolution.
             */
            androidLogger()
            /**
             * Provides Android application context to Koin.
             */
            androidContext(this@BranchLocatorApplication)
            /**
             * Registers all dependency modules used in the app.
             *
             * - repositoryModule: repository bindings
             * - viewModelModule: ViewModel bindings
             * - databaseModule: Room database setup
             * - dataSourceModule: local data sources
             */
            modules(
                repositoryModule,
                viewModelModule,
                databaseModule,
                dataSourceModule
            )
        }
    }
}