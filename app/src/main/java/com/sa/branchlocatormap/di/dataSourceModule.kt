package com.sa.branchlocatormap.di

import com.sa.branchlocatormap.data.dataSource.BankBranchLocalDataSource
import org.koin.dsl.module

val dataSourceModule = module {

    single {
        BankBranchLocalDataSource()
    }
}