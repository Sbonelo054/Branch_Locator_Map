package com.sa.branchlocatormap.data.dataSource

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sa.branchlocatormap.data.dataSource.BranchLocatorDao
import com.sa.branchlocatormap.domain.model.BankBranchDetail
import com.sa.branchlocatormap.domain.model.Converters

/**
 * Main Room database for the Branch Locator feature.
 *
 * This class defines:
 * - The list of entities (tables)
 * - The DAOs used to access the database
 * - The singleton instance of the database
 *
 * Room generates the implementation automatically.
 */
@Database(
    version = 1,
    entities = [BankBranchDetail::class],
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BranchLocatorDatabase : RoomDatabase() {

    /**
     * Provides access to [BranchLocatorDao].
     *
     * This DAO contains all database operations related to bank branches.
     */
    abstract fun branchLocatorDao(): BranchLocatorDao

    companion object {

        /**
         * Singleton instance of the database.
         *
         * Marked as nullable initially and initialized lazily.
         */
        private var instance: BranchLocatorDatabase? = null

        /**
         * Returns the singleton instance of the database.
         *
         * @param application Application context (required to avoid memory leaks)
         *
         * Behavior:
         * - Creates the database if it doesn't exist
         * - Returns the existing instance otherwise
         *
         * Thread safety:
         * - `@Synchronized` ensures only one thread can initialize the database at a time
         */
        @Synchronized
        fun getInstance(application: Application): BranchLocatorDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    application,
                    BranchLocatorDatabase::class.java,
                    "branch_locator_database"
                )
                    /**
                     * Clears and recreates the database if a migration is missing.
                     *
                     * This will delete all stored data on version changes.
                     * Safe for development, risky for production.
                     */
                    .fallbackToDestructiveMigration()

                    /**
                     * Optional database callback.
                     *
                     * Can be used for:
                     * - Pre-populating data
                     * - Logging
                     * - Monitoring database creation/open events
                     */
                    .addCallback(callback)

                    .build()
            }
            return instance
        }

        /**
         * Room database callback.
         *
         * Currently empty, but can be extended to hook into:
         * - onCreate()
         * - onOpen()
         */
        private val callback: Callback = object : Callback() {
        }
    }
}