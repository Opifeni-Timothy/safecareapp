package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.CommunityReport
import com.example.data.model.HouseholdWasteLog
import com.example.data.model.WasteSchedule
import kotlinx.coroutines.flow.Flow

@Dao
interface WasteDao {

    // --- Schedules ---
    @Query("SELECT * FROM waste_schedules ORDER BY id ASC")
    fun getAllSchedules(): Flow<List<WasteSchedule>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: WasteSchedule): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedules(schedules: List<WasteSchedule>)

    @Update
    suspend fun updateSchedule(schedule: WasteSchedule)

    @Query("UPDATE waste_schedules SET isCompletedThisCycle = :isCompleted WHERE id = :id")
    suspend fun toggleScheduleCompletion(id: Long, isCompleted: Boolean)

    @Query("UPDATE waste_schedules SET reminderEnabled = :enabled WHERE id = :id")
    suspend fun toggleScheduleReminder(id: Long, enabled: Boolean)

    @Delete
    suspend fun deleteSchedule(schedule: WasteSchedule)

    @Query("DELETE FROM waste_schedules WHERE id = :id")
    suspend fun deleteScheduleById(id: Long)

    @Query("SELECT COUNT(*) FROM waste_schedules")
    suspend fun getScheduleCount(): Int

    // --- Community Reports ---
    @Query("SELECT * FROM community_reports ORDER BY timestamp DESC")
    fun getAllReports(): Flow<List<CommunityReport>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: CommunityReport): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReports(reports: List<CommunityReport>)

    @Update
    suspend fun updateReport(report: CommunityReport)

    @Query("UPDATE community_reports SET upvotes = upvotes + 1 WHERE id = :id")
    suspend fun upvoteReport(id: Long)

    @Query("UPDATE community_reports SET status = :status WHERE id = :id")
    suspend fun updateReportStatus(id: Long, status: String)

    @Query("SELECT COUNT(*) FROM community_reports")
    suspend fun getReportCount(): Int

    // --- Household Waste Logs ---
    @Query("SELECT * FROM household_waste_logs ORDER BY dateTimestamp DESC")
    fun getAllWasteLogs(): Flow<List<HouseholdWasteLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWasteLog(log: HouseholdWasteLog): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWasteLogs(logs: List<HouseholdWasteLog>)

    @Delete
    suspend fun deleteWasteLog(log: HouseholdWasteLog)

    @Query("SELECT COUNT(*) FROM household_waste_logs")
    suspend fun getWasteLogCount(): Int
}
