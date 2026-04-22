package com.sa.branchlocatormap.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sa.branchlocatormap.domain.BankBranchDetail

@Database(version = 1, entities = [BankBranchDetail::class], exportSchema = false)
abstract class BranchLocatorDatabase: RoomDatabase() {

}