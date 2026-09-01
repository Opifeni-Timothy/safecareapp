package com.example.ui.viewmodel

import com.example.data.model.CommunityDropOffPoint
import com.example.data.model.CommunityReport
import com.example.data.model.HouseholdWasteLog
import com.example.data.model.WasteCategory
import com.example.data.model.WasteSchedule
import com.example.data.model.WasteSortingItem
import com.example.data.repository.WasteGuideDataSource

enum class SafeCareTab(val title: String, val iconName: String) {
    SCHEDULES("Schedules", "calendar_today"),
    GUIDE("Sorting Guide", "menu_book"),
    COMMUNITY("Community Hub", "groups"),
    TRACKER("Eco Tracker", "insights")
}

enum class ScheduleFilter {
    ALL,
    HOUSEHOLD,
    COMMUNITY
}

data class SafeCareUiState(
    val currentTab: SafeCareTab = SafeCareTab.SCHEDULES,
    val schedules: List<WasteSchedule> = emptyList(),
    val scheduleFilter: ScheduleFilter = ScheduleFilter.ALL,
    val selectedZone: String = "All Zones",
    
    // Guide
    val searchQuery: String = "",
    val selectedGuideCategory: WasteCategory? = null,
    val sortingItems: List<WasteSortingItem> = emptyList(),
    val selectedSortingItem: WasteSortingItem? = null,
    val isQuizActive: Boolean = false,
    val quizCurrentQuestionIndex: Int = 0,
    val quizSelectedAnswerIndex: Int? = null,
    val quizScore: Int = 0,
    val quizFinished: Boolean = false,
    
    // Community
    val dropOffPoints: List<CommunityDropOffPoint> = emptyList(),
    val selectedDropOffCategory: WasteCategory? = null,
    val communityReports: List<CommunityReport> = emptyList(),
    val communitySubTab: Int = 0, // 0 = Drop-off Points, 1 = Community Reports
    
    // Eco Tracker
    val wasteLogs: List<HouseholdWasteLog> = emptyList(),
    
    // Dialogs
    val showAddScheduleDialog: Boolean = false,
    val showCreateReportDialog: Boolean = false,
    val showLogWasteDialog: Boolean = false,
    val notificationMessage: String? = null
)
