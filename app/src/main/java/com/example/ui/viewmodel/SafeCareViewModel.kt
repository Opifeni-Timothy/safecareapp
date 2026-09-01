package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.CommunityReport
import com.example.data.model.HouseholdWasteLog
import com.example.data.model.WasteCategory
import com.example.data.model.WasteSchedule
import com.example.data.model.WasteSortingItem
import com.example.data.repository.WasteGuideDataSource
import com.example.data.repository.WasteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SafeCareViewModel(private val repository: WasteRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SafeCareUiState(
            sortingItems = repository.getSortingGuideItems(),
            dropOffPoints = repository.getDropOffPoints()
        )
    )
    val uiState: StateFlow<SafeCareUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.populateInitialDataIfEmpty()
        }

        viewModelScope.launch {
            combine(
                repository.allSchedules,
                repository.allReports,
                repository.allWasteLogs
            ) { schedules, reports, logs ->
                Triple(schedules, reports, logs)
            }.collect { (schedules, reports, logs) ->
                _uiState.update { current ->
                    current.copy(
                        schedules = schedules,
                        communityReports = reports,
                        wasteLogs = logs
                    )
                }
            }
        }
    }

    // --- Tab Navigation ---
    fun selectTab(tab: SafeCareTab) {
        _uiState.update { it.copy(currentTab = tab) }
    }

    // --- Schedules ---
    fun setScheduleFilter(filter: ScheduleFilter) {
        _uiState.update { it.copy(scheduleFilter = filter) }
    }

    fun setSelectedZone(zone: String) {
        _uiState.update { it.copy(selectedZone = zone) }
    }

    fun toggleScheduleCompletion(schedule: WasteSchedule) {
        viewModelScope.launch {
            val newStatus = !schedule.isCompletedThisCycle
            repository.toggleScheduleCompletion(schedule.id, newStatus)
            _uiState.update {
                it.copy(
                    notificationMessage = if (newStatus) "Marked '${schedule.title}' as completed for this cycle"
                    else "Reopened '${schedule.title}'"
                )
            }
        }
    }

    fun toggleScheduleReminder(schedule: WasteSchedule) {
        viewModelScope.launch {
            val newStatus = !schedule.reminderEnabled
            repository.toggleScheduleReminder(schedule.id, newStatus)
            _uiState.update {
                it.copy(
                    notificationMessage = if (newStatus) "Reminder activated for ${schedule.title}"
                    else "Reminder turned off for ${schedule.title}"
                )
            }
        }
    }

    fun addSchedule(schedule: WasteSchedule) {
        viewModelScope.launch {
            repository.insertSchedule(schedule)
            _uiState.update {
                it.copy(
                    showAddScheduleDialog = false,
                    notificationMessage = "New schedule '${schedule.title}' saved"
                )
            }
        }
    }

    fun deleteSchedule(schedule: WasteSchedule) {
        viewModelScope.launch {
            repository.deleteSchedule(schedule.id)
            _uiState.update {
                it.copy(notificationMessage = "Schedule deleted")
            }
        }
    }

    fun setShowAddScheduleDialog(show: Boolean) {
        _uiState.update { it.copy(showAddScheduleDialog = show) }
    }

    // --- Sorting Guide ---
    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun selectGuideCategory(category: WasteCategory?) {
        _uiState.update { it.copy(selectedGuideCategory = category) }
    }

    fun selectSortingItem(item: WasteSortingItem?) {
        _uiState.update { it.copy(selectedSortingItem = item) }
    }

    // --- Quiz ---
    fun startQuiz() {
        _uiState.update {
            it.copy(
                isQuizActive = true,
                quizCurrentQuestionIndex = 0,
                quizSelectedAnswerIndex = null,
                quizScore = 0,
                quizFinished = false
            )
        }
    }

    fun answerQuizQuestion(selectedIndex: Int) {
        val currentState = _uiState.value
        val questions = repository.getQuizQuestions()
        if (currentState.quizCurrentQuestionIndex >= questions.size) return

        val currentQuestion = questions[currentState.quizCurrentQuestionIndex]
        val isCorrect = selectedIndex == currentQuestion.correctIndex
        val newScore = if (isCorrect) currentState.quizScore + 1 else currentState.quizScore

        _uiState.update {
            it.copy(
                quizSelectedAnswerIndex = selectedIndex,
                quizScore = newScore
            )
        }
    }

    fun nextQuizQuestion() {
        val currentState = _uiState.value
        val questions = repository.getQuizQuestions()
        if (currentState.quizCurrentQuestionIndex + 1 < questions.size) {
            _uiState.update {
                it.copy(
                    quizCurrentQuestionIndex = it.quizCurrentQuestionIndex + 1,
                    quizSelectedAnswerIndex = null
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    quizFinished = true,
                    quizSelectedAnswerIndex = null
                )
            }
        }
    }

    fun closeQuiz() {
        _uiState.update {
            it.copy(
                isQuizActive = false,
                quizFinished = false,
                quizSelectedAnswerIndex = null
            )
        }
    }

    // --- Community Hub ---
    fun setCommunitySubTab(index: Int) {
        _uiState.update { it.copy(communitySubTab = index) }
    }

    fun selectDropOffCategory(category: WasteCategory?) {
        _uiState.update { it.copy(selectedDropOffCategory = category) }
    }

    fun upvoteReport(report: CommunityReport) {
        viewModelScope.launch {
            repository.upvoteReport(report.id)
            _uiState.update {
                it.copy(notificationMessage = "Upvoted issue: ${report.title}")
            }
        }
    }

    fun addCommunityReport(report: CommunityReport) {
        viewModelScope.launch {
            repository.insertReport(report)
            _uiState.update {
                it.copy(
                    showCreateReportDialog = false,
                    notificationMessage = "Community report posted successfully"
                )
            }
        }
    }

    fun setShowCreateReportDialog(show: Boolean) {
        _uiState.update { it.copy(showCreateReportDialog = show) }
    }

    // --- Eco Tracker & Logs ---
    fun addHouseholdWasteLog(log: HouseholdWasteLog) {
        viewModelScope.launch {
            repository.insertWasteLog(log)
            _uiState.update {
                it.copy(
                    showLogWasteDialog = false,
                    notificationMessage = "Logged ${log.amountKg} kg of ${log.categoryEnum.displayName} diverted"
                )
            }
        }
    }

    fun deleteHouseholdWasteLog(log: HouseholdWasteLog) {
        viewModelScope.launch {
            repository.deleteWasteLog(log)
            _uiState.update {
                it.copy(notificationMessage = "Log entry removed")
            }
        }
    }

    fun setShowLogWasteDialog(show: Boolean) {
        _uiState.update { it.copy(showLogWasteDialog = show) }
    }

    fun clearNotificationMessage() {
        _uiState.update { it.copy(notificationMessage = null) }
    }
}

class SafeCareViewModelFactory(private val repository: WasteRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SafeCareViewModel::class.java)) {
            return SafeCareViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
