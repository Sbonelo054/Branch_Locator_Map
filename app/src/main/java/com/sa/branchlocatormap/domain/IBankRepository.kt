package com.sa.branchlocatormap.domain

import kotlinx.coroutines.flow.StateFlow

/**
 * Contract for the Bank Repository.
 *
 * This interface defines the operations related to retrieving and managing
 * bank branch data, regardless of where the data comes from
 * (local source, remote API, cache, etc.).
 *
 * It acts as an abstraction layer between:
 * - ViewModels (UI layer)
 * - Data sources (local/remote/database)
 */
interface IBankRepository {

    /**
     * Reactive stream of bank branches.
     *
     * Behavior:
     * - Emits the current list of branches
     * - Updates automatically when data changes
     *
     * Used by UI/ViewModels to observe branch data.
     */
    val branches: StateFlow<List<BankBranchDetail>>

    /**
     * Triggers loading of bank branches from the underlying data source.
     *
     * Implementation details may include:
     * - Loading from local data source
     * - Fetching from remote API
     * - Merging multiple sources
     *
     * Note:
     * In more advanced designs, this would typically be a `suspend` function
     * or handled automatically via reactive streams instead of manual calls.
     */
    fun loadBranches()
}