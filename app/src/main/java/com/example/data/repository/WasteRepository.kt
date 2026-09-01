package com.example.data.repository

import com.example.data.local.WasteDao
import com.example.data.model.CommunityDropOffPoint
import com.example.data.model.CommunityReport
import com.example.data.model.HouseholdWasteLog
import com.example.data.model.WasteCategory
import com.example.data.model.WasteSchedule
import com.example.data.model.WasteSortingItem
import kotlinx.coroutines.flow.Flow

class WasteRepository(private val wasteDao: WasteDao) {

    val allSchedules: Flow<List<WasteSchedule>> = wasteDao.getAllSchedules()
    val allReports: Flow<List<CommunityReport>> = wasteDao.getAllReports()
    val allWasteLogs: Flow<List<HouseholdWasteLog>> = wasteDao.getAllWasteLogs()

    suspend fun populateInitialDataIfEmpty() {
        if (wasteDao.getScheduleCount() == 0) {
            wasteDao.insertSchedules(WasteGuideDataSource.initialSchedules)
        }
        if (wasteDao.getReportCount() == 0) {
            wasteDao.insertReports(WasteGuideDataSource.sampleReports)
        }
        if (wasteDao.getWasteLogCount() == 0) {
            wasteDao.insertWasteLogs(WasteGuideDataSource.sampleWasteLogs)
        }
    }

    suspend fun insertSchedule(schedule: WasteSchedule): Long {
        return wasteDao.insertSchedule(schedule)
    }

    suspend fun updateSchedule(schedule: WasteSchedule) {
        wasteDao.updateSchedule(schedule)
    }

    suspend fun toggleScheduleCompletion(id: Long, isCompleted: Boolean) {
        wasteDao.toggleScheduleCompletion(id, isCompleted)
    }

    suspend fun toggleScheduleReminder(id: Long, enabled: Boolean) {
        wasteDao.toggleScheduleReminder(id, enabled)
    }

    suspend fun deleteSchedule(id: Long) {
        wasteDao.deleteScheduleById(id)
    }

    suspend fun insertReport(report: CommunityReport): Long {
        return wasteDao.insertReport(report)
    }

    suspend fun upvoteReport(id: Long) {
        wasteDao.upvoteReport(id)
    }

    suspend fun updateReportStatus(id: Long, status: String) {
        wasteDao.updateReportStatus(id, status)
    }

    suspend fun insertWasteLog(log: HouseholdWasteLog): Long {
        return wasteDao.insertWasteLog(log)
    }

    suspend fun deleteWasteLog(log: HouseholdWasteLog) {
        wasteDao.deleteWasteLog(log)
    }

    fun getSortingGuideItems(): List<WasteSortingItem> {
        return WasteGuideDataSource.sortingItems
    }

    fun getDropOffPoints(): List<CommunityDropOffPoint> {
        return WasteGuideDataSource.dropOffPoints
    }

    fun getQuizQuestions(): List<WasteGuideDataSource.QuizQuestion> {
        return WasteGuideDataSource.quizQuestions
    }
}
