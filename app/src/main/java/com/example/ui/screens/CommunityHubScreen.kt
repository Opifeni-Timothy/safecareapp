package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CommunityDropOffPoint
import com.example.data.model.CommunityReport
import com.example.data.model.ReportStatus
import com.example.data.model.WasteCategory
import com.example.ui.components.CategoryBadge
import com.example.ui.components.getCategoryIcon
import com.example.ui.theme.CompostGreen
import com.example.ui.theme.VibrantBorder
import com.example.ui.theme.VibrantBorderLight
import com.example.ui.theme.VibrantDarkGreen
import com.example.ui.theme.VibrantGreen
import com.example.ui.theme.VibrantMutedText
import com.example.ui.theme.VibrantSageContainer
import com.example.ui.theme.VibrantSageLight
import com.example.ui.viewmodel.SafeCareUiState
import com.example.ui.viewmodel.SafeCareViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityHubScreen(
    uiState: SafeCareUiState,
    viewModel: SafeCareViewModel,
    modifier: Modifier = Modifier
) {
    val filteredDropOffs = uiState.dropOffPoints.filter { point ->
        uiState.selectedDropOffCategory == null || point.category == uiState.selectedDropOffCategory
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        floatingActionButton = {
            if (uiState.communitySubTab == 1) {
                FloatingActionButton(
                    onClick = { viewModel.setShowCreateReportDialog(true) },
                    containerColor = VibrantGreen,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.testTag("report_issue_fab")
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Report Issue")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Sub-Tab Switch
            PrimaryTabRow(
                selectedTabIndex = uiState.communitySubTab,
                containerColor = Color.White,
                contentColor = VibrantGreen,
                modifier = Modifier.fillMaxWidth()
            ) {
                Tab(
                    selected = uiState.communitySubTab == 0,
                    onClick = { viewModel.setCommunitySubTab(0) },
                    text = {
                        Text(
                            text = "Drop-Off Depots & Hubs",
                            fontWeight = if (uiState.communitySubTab == 0) FontWeight.Bold else FontWeight.Normal,
                            color = if (uiState.communitySubTab == 0) VibrantDarkGreen else VibrantMutedText
                        )
                    },
                    modifier = Modifier.testTag("subtab_dropoff_points")
                )
                Tab(
                    selected = uiState.communitySubTab == 1,
                    onClick = { viewModel.setCommunitySubTab(1) },
                    text = {
                        Text(
                            text = "Cleanup & Issue Reports",
                            fontWeight = if (uiState.communitySubTab == 1) FontWeight.Bold else FontWeight.Normal,
                            color = if (uiState.communitySubTab == 1) VibrantDarkGreen else VibrantMutedText
                        )
                    },
                    modifier = Modifier.testTag("subtab_community_reports")
                )
            }

            if (uiState.communitySubTab == 0) {
                // Drop-Off Points Tab
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 88.dp)
                ) {
                    item {
                        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                            Text(
                                text = "Filter by Disposal Service",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = VibrantDarkGreen
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                item {
                                    FilterChip(
                                        selected = uiState.selectedDropOffCategory == null,
                                        onClick = { viewModel.selectDropOffCategory(null) },
                                        label = { Text("All Depots") },
                                        shape = RoundedCornerShape(20.dp),
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = VibrantSageContainer,
                                            selectedLabelColor = VibrantDarkGreen,
                                            containerColor = Color.White,
                                            labelColor = VibrantMutedText
                                        ),
                                        border = FilterChipDefaults.filterChipBorder(
                                            enabled = true,
                                            selected = uiState.selectedDropOffCategory == null,
                                            borderColor = if (uiState.selectedDropOffCategory == null) VibrantBorder else VibrantBorderLight
                                        )
                                    )
                                }
                                items(listOf(WasteCategory.SPECIAL_DROP_OFF, WasteCategory.HAZARDOUS, WasteCategory.COMPOST)) { cat ->
                                    val isSelected = uiState.selectedDropOffCategory == cat
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = {
                                            viewModel.selectDropOffCategory(if (isSelected) null else cat)
                                        },
                                        label = { Text(cat.displayName) },
                                        leadingIcon = {
                                            Icon(
                                                imageVector = getCategoryIcon(cat),
                                                contentDescription = null,
                                                tint = if (isSelected) VibrantGreen else cat.color,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        },
                                        shape = RoundedCornerShape(20.dp),
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = VibrantSageContainer,
                                            selectedLabelColor = VibrantDarkGreen,
                                            containerColor = Color.White,
                                            labelColor = VibrantMutedText
                                        ),
                                        border = FilterChipDefaults.filterChipBorder(
                                            enabled = true,
                                            selected = isSelected,
                                            borderColor = if (isSelected) VibrantBorder else VibrantBorderLight
                                        )
                                    )
                                }
                            }
                        }
                    }

                    items(filteredDropOffs, key = { it.id }) { point ->
                        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                            DropOffPointCard(point = point)
                        }
                    }
                }
            } else {
                // Community Reports Tab
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 88.dp)
                ) {
                    item {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            shape = RoundedCornerShape(20.dp),
                            color = VibrantSageContainer,
                            border = BorderStroke(1.dp, VibrantBorder)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(VibrantGreen),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Eco,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Community Care Action",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = VibrantDarkGreen
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Report overflowing public bins, illegal dumping, or request a neighborhood cleanup. Upvote issues to alert municipal crews.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = VibrantMutedText
                                    )
                                }
                            }
                        }
                    }

                    if (uiState.communityReports.isEmpty()) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(40.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "No active community reports",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = VibrantDarkGreen
                                )
                                Text(
                                    text = "Your neighborhood is looking clean! Tap + to report an issue if you see one.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = VibrantMutedText
                                )
                            }
                        }
                    } else {
                        items(uiState.communityReports, key = { it.id }) { report ->
                            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                                CommunityReportCard(
                                    report = report,
                                    onUpvote = { viewModel.upvoteReport(report) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DropOffPointCard(
    point: CommunityDropOffPoint,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("dropoff_card_${point.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = BorderStroke(1.dp, VibrantBorderLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CategoryBadge(category = point.category, compact = true)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(VibrantSageLight)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${point.distanceKm} km away",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = VibrantDarkGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = point.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = VibrantDarkGreen
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = VibrantGreen,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = point.address,
                    style = MaterialTheme.typography.bodySmall,
                    color = VibrantMutedText
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    tint = VibrantMutedText,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = point.operatingHours,
                    style = MaterialTheme.typography.bodySmall,
                    color = VibrantMutedText
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Accepted Materials
            Text(
                text = "Accepted Materials:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = VibrantDarkGreen
            )
            Spacer(modifier = Modifier.height(4.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                items(point.acceptedItems) { material ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = VibrantSageLight,
                        border = BorderStroke(0.5.dp, VibrantBorderLight)
                    ) {
                        Text(
                            text = material,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = VibrantDarkGreen,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Resident Tips
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = point.category.lightBgColor,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = point.category.color,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = point.tips,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 11.sp,
                        color = VibrantDarkGreen
                    )
                }
            }
        }
    }
}

@Composable
fun CommunityReportCard(
    report: CommunityReport,
    onUpvote: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateStr = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault()).format(Date(report.timestamp))
    val statusColor = when (report.statusEnum) {
        ReportStatus.REPORTED -> Color(0xFFE65100)
        ReportStatus.IN_PROGRESS -> Color(0xFF1565C0)
        ReportStatus.RESOLVED -> VibrantGreen
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("report_card_${report.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = BorderStroke(1.dp, VibrantBorderLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(statusColor.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = report.statusEnum.label,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor
                    )
                }

                Text(
                    text = dateStr,
                    style = MaterialTheme.typography.bodySmall,
                    color = VibrantMutedText
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = report.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = VibrantDarkGreen
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = VibrantGreen,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${report.location} (${report.neighborhood})",
                    style = MaterialTheme.typography.bodySmall,
                    color = VibrantMutedText,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = report.description,
                style = MaterialTheme.typography.bodyMedium,
                color = VibrantDarkGreen,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Footer with Reporter & Upvote Priority Action
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reported by: ${report.reporterName}",
                    style = MaterialTheme.typography.bodySmall,
                    color = VibrantMutedText
                )

                OutlinedButton(
                    onClick = onUpvote,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, VibrantBorderLight),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = VibrantGreen
                    ),
                    modifier = Modifier.testTag("upvote_report_${report.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = "Upvote priority",
                        modifier = Modifier.size(16.dp),
                        tint = VibrantGreen
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Priority (${report.upvotes})",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = VibrantGreen
                    )
                }
            }
        }
    }
}

