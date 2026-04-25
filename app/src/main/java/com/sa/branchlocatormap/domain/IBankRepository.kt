package com.sa.branchlocatormap.domain

import kotlinx.coroutines.flow.StateFlow

interface IBankRepository {

    val branches: StateFlow<List<BankBranchDetail>>

    fun loadBranches()
}