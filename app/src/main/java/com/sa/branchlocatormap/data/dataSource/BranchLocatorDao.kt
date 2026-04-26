package com.sa.branchlocatormap.data.dataSource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sa.branchlocatormap.domain.model.BankBranchDetail
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for managing bank branch favourites.
 *
 * This interface defines how the app interacts with the local Room database
 * for storing and retrieving favourite branches.
 *
 * Room generates the implementation at compile time.
 */
@Dao
interface BranchLocatorDao {

    /**
     * Inserts or updates a favourite bank branch.
     *
     * @param bankBranchDetail The branch to be saved as favourite.
     *
     * Behavior:
     * - If the branch already exists (based on primary key),
     *   it will be replaced with the new data.
     *
     * Notes:
     * - Marked as `suspend` because database operations should not run on the main thread.
     */
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun saveFavourite(bankBranchDetail: BankBranchDetail)

    /**
     * Retrieves all favourite branches from the database.
     *
     * @return A [kotlinx.coroutines.flow.Flow] that emits a list of favourite branches.
     *
     * Behavior:
     * - Automatically emits updates whenever the database table changes.
     * - Ideal for observing data in real-time in the UI.
     *
     * Notes:
     * - This is not a suspend function because Flow handles async streaming.
     */
    @Query("SELECT * FROM bank_branch_detail")
    fun getFavourites(): Flow<List<BankBranchDetail>>

    /**
     * Removes a branch from favourites.
     *
     * @param branch The branch to delete.
     *
     * Notes:
     * - Uses the object’s primary key to identify which row to delete.
     * - Marked as `suspend` to ensure it runs off the main thread.
     */
    @Delete
    suspend fun removeFavourite(branch: BankBranchDetail)
}