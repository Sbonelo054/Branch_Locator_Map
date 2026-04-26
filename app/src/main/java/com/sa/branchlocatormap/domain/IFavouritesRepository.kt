package com.sa.branchlocatormap.domain

import kotlinx.coroutines.flow.Flow

/**
 * Contract for managing favourite bank branches.
 *
 * This repository defines how favourite branches are stored,
 * retrieved, and removed, regardless of the underlying implementation
 * (e.g. Room database, remote API, in-memory cache).
 *
 * It is part of the data layer abstraction in MVVM architecture.
 */
interface IFavouritesRepository {

    /**
     * Saves a bank branch as a favourite.
     *
     * @param bankBranchDetail The branch to persist as favourite.
     */
    suspend fun saveFavouriteBranch(bankBranchDetail: BankBranchDetail)

    /**
     * Retrieves all favourite bank branches as a reactive stream.
     *
     * @return A [Flow] emitting the current list of favourites.
     *
     * Behavior:
     * - Emits updates automatically when the database changes.
     */
    suspend fun getFavouriteBranches(): Flow<List<BankBranchDetail>>

    /**
     * Removes a branch from favourites.
     *
     * @param branch The branch to delete from favourites.
     */
    suspend fun deleteFavourite(branch: BankBranchDetail)
}