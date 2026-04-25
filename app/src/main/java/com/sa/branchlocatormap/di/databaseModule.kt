package com.sa.branchlocatormap.di

import com.sa.branchlocatormap.data.BranchLocatorDao
import com.sa.branchlocatormap.data.BranchLocatorDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    single {
        BranchLocatorDatabase.getInstance(androidApplication())
    }

    single<BranchLocatorDao> {
        get<BranchLocatorDatabase>().branchLocatorDao()
    }
}