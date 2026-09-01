package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.WasteSchedule
import com.example.ui.components.CategoryBadge
import com.example.ui.components.ScheduleCard
import com.example.ui.components.getCategoryIcon
import com.example.ui.theme.VibrantBorder
import com.example.ui.theme.VibrantBorderLight
import com.example.ui.theme.VibrantBorderMuted
import com.example.ui.theme.VibrantDarkGreen
import com.example.ui.theme.VibrantGreen
import com.example.ui.theme.VibrantMutedText
import com.example.ui.theme.VibrantSageContainer
import com.example.ui.theme.VibrantSageLight
import com.example.ui.viewmodel.SafeCareUiState
import com.example.ui.viewmodel.SafeCareViewModel
import com.example.ui.viewmodel.ScheduleFilter

@Composable
fun ScheduleScreen(
    uiState: SafeCareUiState,
    viewModel: SafeCareViewModel,
    modifier: Modifier = Modifier
) {
    val filteredSchedules = uiState.schedules.filter { schedule ->
        val matchesType = when (uiState.scheduleFilter) {
            ScheduleFilter.ALL -> true
            ScheduleFilter.HOUSEHOLD -> !schedule.isCommunitySchedule
            ScheduleFilter.COMMUNITY -> schedule.isCommunitySchedule
        }
        val matchesZone = uiState.selectedZone == "All Zones" || schedule.zoneOrAddress.contains(uiState.selectedZone, ignoreCase = true)
        matchesType && matchesZone
    }

    val nextUpcomingSchedule = uiState.schedules.firstOrNull { !it.isCompletedThisCycle }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.setShowAddScheduleDialog(true) },
                containerColor = VibrantGreen,
                contentColor = Color.White,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.testTag("add_schedule_fab")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Schedule")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 88.dp)
        ) {
            // Next Collection Featured Hero Card (Vibrant Palette Design)
            if (nextUpcomingSchedule != null) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .testTag("next_collection_card"),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(containerColor = VibrantSageContainer),
                        border = BorderStroke(1.dp, VibrantBorder),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(22.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column {
                                    Text(
                                        text = "NEXT COLLECTION",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = VibrantGreen,
                                        letterSpacing = 1.5.sp
                                    )
                                    Text(
                                        text = nextUpcomingSchedule.dayOfWeek,
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.Black,
                                        color = VibrantDarkGreen
                                    )
                                    Text(
                                        text = nextUpcomingSchedule.title,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = VibrantMutedText
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color.White,
                                    shadowElevation = 2.dp,
                                    modifier = Modifier.size(48.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(6.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(VibrantGreen),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = getCategoryIcon(nextUpcomingSchedule.categoryEnum),
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Badges row
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(VibrantGreen)
                                        .padding(horizontal = 14.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = nextUpcomingSchedule.categoryEnum.displayName,
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(VibrantBorderLight)
                                        .padding(horizontal = 14.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = nextUpcomingSchedule.frequencyEnum.label,
                                        color = VibrantMutedText,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(18.dp))
                            HorizontalDivider(color = VibrantBorderMuted.copy(alpha = 0.6f))
                            Spacer(modifier = Modifier.height(14.dp))

                            // Bottom reminder status
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Reminder set for ${nextUpcomingSchedule.timeOfDay}",
                                    fontSize = 13.sp,
                                    color = VibrantMutedText,
                                    fontWeight = FontWeight.Medium
                                )

                                Switch(
                                    checked = nextUpcomingSchedule.reminderEnabled,
                                    onCheckedChange = { viewModel.toggleScheduleReminder(nextUpcomingSchedule) },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = VibrantGreen,
                                        uncheckedThumbColor = VibrantMutedText,
                                        uncheckedTrackColor = VibrantBorderLight
                                    ),
                                    modifier = Modifier.testTag("next_schedule_reminder_switch")
                                )
                            }
                        }
                    }
                }
            }

            // Hero Banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(24.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hero_waste_sorting),
                        contentDescription = "Waste Sorting Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color(0xCC101F02))
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Household & Neighborhood Bins",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Never miss a pickup. Keep bins sorted & clean.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFBFC8B9)
                        )
                    }
                }
            }

            // Filter Chips Row
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text(
                        text = "Filter Collection Types",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = VibrantDarkGreen
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            FilterChip(
                                selected = uiState.scheduleFilter == ScheduleFilter.ALL,
                                onClick = { viewModel.setScheduleFilter(ScheduleFilter.ALL) },
                                label = { Text("All Schedules") },
                                leadingIcon = if (uiState.scheduleFilter == ScheduleFilter.ALL) {
                                    { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                                } else null,
                                shape = RoundedCornerShape(20.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = VibrantSageContainer,
                                    selectedLabelColor = VibrantDarkGreen,
                                    containerColor = Color.White,
                                    labelColor = VibrantMutedText
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = uiState.scheduleFilter == ScheduleFilter.ALL,
                                    borderColor = if (uiState.scheduleFilter == ScheduleFilter.ALL) VibrantBorder else VibrantBorderLight
                                ),
                                modifier = Modifier.testTag("filter_all_schedules")
                            )
                        }
                        item {
                            FilterChip(
                                selected = uiState.scheduleFilter == ScheduleFilter.HOUSEHOLD,
                                onClick = { viewModel.setScheduleFilter(ScheduleFilter.HOUSEHOLD) },
                                label = { Text("Household Curbside") },
                                leadingIcon = if (uiState.scheduleFilter == ScheduleFilter.HOUSEHOLD) {
                                    { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                                } else null,
                                shape = RoundedCornerShape(20.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = VibrantSageContainer,
                                    selectedLabelColor = VibrantDarkGreen,
                                    containerColor = Color.White,
                                    labelColor = VibrantMutedText
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = uiState.scheduleFilter == ScheduleFilter.HOUSEHOLD,
                                    borderColor = if (uiState.scheduleFilter == ScheduleFilter.HOUSEHOLD) VibrantBorder else VibrantBorderLight
                                ),
                                modifier = Modifier.testTag("filter_household_schedules")
                            )
                        }
                        item {
                            FilterChip(
                                selected = uiState.scheduleFilter == ScheduleFilter.COMMUNITY,
                                onClick = { viewModel.setScheduleFilter(ScheduleFilter.COMMUNITY) },
                                label = { Text("Community & Bulk") },
                                leadingIcon = if (uiState.scheduleFilter == ScheduleFilter.COMMUNITY) {
                                    { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                                } else null,
                                shape = RoundedCornerShape(20.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = VibrantSageContainer,
                                    selectedLabelColor = VibrantDarkGreen,
                                    containerColor = Color.White,
                                    labelColor = VibrantMutedText
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = uiState.scheduleFilter == ScheduleFilter.COMMUNITY,
                                    borderColor = if (uiState.scheduleFilter == ScheduleFilter.COMMUNITY) VibrantBorder else VibrantBorderLight
                                ),
                                modifier = Modifier.testTag("filter_community_schedules")
                            )
                        }
                    }
                }
            }

            // Schedules List
            if (filteredSchedules.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = null,
                            tint = VibrantMutedText.copy(alpha = 0.5f),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No collection schedules found",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = VibrantDarkGreen
                        )
                        Text(
                            text = "Tap the + button below to add your neighborhood pickup schedule.",
                            style = MaterialTheme.typography.bodySmall,
                            color = VibrantMutedText
                        )
                    }
                }
            } else {
                items(filteredSchedules, key = { it.id }) { schedule ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                        ScheduleCard(
                            schedule = schedule,
                            onToggleCompletion = { viewModel.toggleScheduleCompletion(schedule) },
                            onToggleReminder = { viewModel.toggleScheduleReminder(schedule) },
                            onDelete = { viewModel.deleteSchedule(schedule) }
                        )
                    }
                }
            }
        }
    }
}

