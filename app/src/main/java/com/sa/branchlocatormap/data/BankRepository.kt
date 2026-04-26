package com.sa.branchlocatormap.data

import com.sa.branchlocatormap.data.dataSource.BankBranchLocalDataSource
import com.sa.branchlocatormap.domain.BankBranchDetail
import com.sa.branchlocatormap.domain.IBankRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BankRepository( private val localDataSource: BankBranchLocalDataSource): IBankRepository {

    private val _branches = MutableStateFlow<List<BankBranchDetail>>(emptyList())

    override val branches: StateFlow<List<BankBranchDetail>> = _branches

    init {
        loadBranches()
    }

     override fun loadBranches() {
        _branches.value = localDataSource.bankBranches
    }
}