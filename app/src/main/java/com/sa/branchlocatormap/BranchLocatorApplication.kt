package com.sa.branchlocatormap

import android.app.Application
import com.sa.branchlocatormap.di.repositoryModule
import com.sa.branchlocatormap.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BranchLocatorApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BranchLocatorApplication)
            modules(repositoryModule, viewModelModule)
        }
    }
}