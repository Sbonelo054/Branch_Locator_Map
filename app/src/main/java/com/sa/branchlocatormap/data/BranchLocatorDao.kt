package com.sa.branchlocatormap.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sa.branchlocatormap.domain.BankBranchDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface BranchLocatorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavourite(bankBranchDetail: BankBranchDetail)

    @Query("SELECT * FROM bank_branch_detail")
    fun getFavourites(): Flow<List<BankBranchDetail>>

    @Delete
    suspend fun removeFavourite(branch: BankBranchDetail)

}