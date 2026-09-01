package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.SafeCareDatabase
import com.example.data.repository.WasteRepository
import com.example.ui.components.AddScheduleDialog
import com.example.ui.components.CreateReportDialog
import com.example.ui.components.ItemDetailBottomSheet
import com.example.ui.components.LogWasteDialog
import com.example.ui.components.QuizDialog
import com.example.ui.components.SafeCareBottomNav
import com.example.ui.components.SafeCareTopAppBar
import com.example.ui.screens.CommunityHubScreen
import com.example.ui.screens.EcoTrackerScreen
import com.example.ui.screens.ScheduleScreen
import com.example.ui.screens.SortingGuideScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.SafeCareTab
import com.example.ui.viewmodel.SafeCareViewModel
import com.example.ui.viewmodel.SafeCareViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                SafeCareApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SafeCareApp() {
    val context = LocalContext.current
    val database = remember { SafeCareDatabase.getDatabase(context) }
    val repository = remember { WasteRepository(database.wasteDao()) }
    val viewModel: SafeCareViewModel = viewModel(
        factory = SafeCareViewModelFactory(repository)
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Notify on snackbar events
    LaunchedEffect(uiState.notificationMessage) {
        uiState.notificationMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearNotificationMessage()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SafeCareTopAppBar(
                currentTab = uiState.currentTab,
                onOpenQuiz = { viewModel.startQuiz() }
            )
        },
        bottomBar = {
            SafeCareBottomNav(
                selectedTab = uiState.currentTab,
                onTabSelected = { viewModel.selectTab(it) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.currentTab) {
                SafeCareTab.SCHEDULES -> ScheduleScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                SafeCareTab.GUIDE -> SortingGuideScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                SafeCareTab.COMMUNITY -> CommunityHubScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                SafeCareTab.TRACKER -> EcoTrackerScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
            }
        }
    }

    // Item Detail Bottom Sheet
    if (uiState.selectedSortingItem != null) {
        ItemDetailBottomSheet(
            item = uiState.selectedSortingItem!!,
            onDismiss = { viewModel.selectSortingItem(null) },
            sheetState = sheetState
        )
    }

    // Add Schedule Dialog
    if (uiState.showAddScheduleDialog) {
        AddScheduleDialog(
            onDismiss = { viewModel.setShowAddScheduleDialog(false) },
            onConfirm = { newSchedule ->
                viewModel.addSchedule(newSchedule)
            }
        )
    }

    // Create Community Report Dialog
    if (uiState.showCreateReportDialog) {
        CreateReportDialog(
            onDismiss = { viewModel.setShowCreateReportDialog(false) },
            onConfirm = { newReport ->
                viewModel.addCommunityReport(newReport)
            }
        )
    }

    // Log Waste Dialog
    if (uiState.showLogWasteDialog) {
        LogWasteDialog(
            onDismiss = { viewModel.setShowLogWasteDialog(false) },
            onConfirm = { newLog ->
                viewModel.addHouseholdWasteLog(newLog)
            }
        )
    }

    // Sorting Quiz Dialog
    if (uiState.isQuizActive) {
        val questions = repository.getQuizQuestions()
        QuizDialog(
            questions = questions,
            currentIndex = uiState.quizCurrentQuestionIndex,
            selectedIndex = uiState.quizSelectedAnswerIndex,
            score = uiState.quizScore,
            isFinished = uiState.quizFinished,
            onAnswerSelected = { ansIndex ->
                viewModel.answerQuizQuestion(ansIndex)
            },
            onNext = { viewModel.nextQuizQuestion() },
            onClose = { viewModel.closeQuiz() },
            onRestart = { viewModel.startQuiz() }
        )
    }
}
