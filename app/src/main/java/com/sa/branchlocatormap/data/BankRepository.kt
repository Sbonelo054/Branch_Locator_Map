package com.sa.branchlocatormap.data

import com.sa.branchlocatormap.data.dataSource.BankBranchLocalDataSource
import com.sa.branchlocatormap.domain.BankBranchDetail
import com.sa.branchlocatormap.domain.IBankRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Concrete implementation of [IBankRepository].
 *
 * This repository is responsible for exposing bank branch data to the rest
 * of the app while hiding the underlying data source (currently local only).
 *
 * It uses Kotlin Flow ([StateFlow]) to provide observable, reactive updates.
 */
class BankRepository(
    private val localDataSource: BankBranchLocalDataSource
) : IBankRepository {

    /**
     * Internal mutable state holding the list of bank branches.
     *
     * Starts as an empty list and gets populated when [loadBranches] is called.
     */
    private val _branches = MutableStateFlow<List<BankBranchDetail>>(emptyList())

    /**
     * Public immutable stream of bank branches.
     *
     * Consumers (e.g., ViewModels or UI) collect this flow to receive updates.
     * They cannot modify the data directly.
     */
    override val branches: StateFlow<List<BankBranchDetail>> = _branches

    /**
     * Automatically loads data when the repository is instantiated.
     *
     * This ensures that observers immediately receive data without needing
     * to explicitly trigger a load.
     */
    init {
        loadBranches()
    }

    /**
     * Loads bank branch data from the local data source.
     *
     * Since the data source is local and synchronous, this operation is immediate.
     * The result is pushed into [_branches], notifying all collectors.
     *
     * Future considerations:
     * - Convert to `suspend` if switching to a remote API or database.
     * - Add error handling and loading states.
     * - Merge multiple data sources (local cache + remote).
     */
    override fun loadBranches() {
        _branches.value = localDataSource.bankBranches
    }
}