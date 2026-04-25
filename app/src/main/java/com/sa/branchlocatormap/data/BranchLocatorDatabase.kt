package com.sa.branchlocatormap.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sa.branchlocatormap.domain.BankBranchDetail
import com.sa.branchlocatormap.domain.Converters

@Database(version = 1, entities = [BankBranchDetail::class], exportSchema = false)
@TypeConverters(Converters::class)
abstract class BranchLocatorDatabase: RoomDatabase() {
    abstract fun branchLocatorDao(): BranchLocatorDao

    companion object {
        private var instance: BranchLocatorDatabase? = null

        @Synchronized
        fun getInstance(application: Application): BranchLocatorDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    application,
                    BranchLocatorDatabase::class.java, "branch_locator_database"
                ).fallbackToDestructiveMigration().addCallback(callback).build()
            }
            return instance
        }

        private val callback: Callback = object : Callback() { }
    }
}