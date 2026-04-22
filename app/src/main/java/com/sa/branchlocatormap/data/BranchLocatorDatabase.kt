package com.sa.branchlocatormap.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sa.branchlocatormap.domain.BankBranchDetail
import com.sa.branchlocatormap.domain.Converters

@Database(version = 1, entities = [BankBranchDetail::class], exportSchema = false)
@TypeConverters(Converters::class)
abstract class BranchLocatorDatabase: RoomDatabase() {

}