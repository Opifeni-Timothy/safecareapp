package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.CommunityReport
import com.example.data.model.HouseholdWasteLog
import com.example.data.model.WasteSchedule

@Database(
    entities = [
        WasteSchedule::class,
        CommunityReport::class,
        HouseholdWasteLog::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SafeCareDatabase : RoomDatabase() {

    abstract fun wasteDao(): WasteDao

    companion object {
        @Volatile
        private var INSTANCE: SafeCareDatabase? = null

        fun getDatabase(context: Context): SafeCareDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SafeCareDatabase::class.java,
                    "safecare_waste_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
