package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Co2
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HouseholdWasteLog
import com.example.data.model.WasteCategory
import com.example.ui.components.CategoryBadge
import com.example.ui.theme.CompostGreen
import com.example.ui.theme.HazardousAmber
import com.example.ui.theme.RecycleBlue
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class EcoBadge(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val isUnlocked: Boolean,
    val badgeColor: Color
)

@Composable
fun EcoTrackerScreen(
    uiState: SafeCareUiState,
    viewModel: SafeCareViewModel,
    modifier: Modifier = Modifier
) {
    val totalKgDiverted = uiState.wasteLogs
        .filter { it.categoryEnum != WasteCategory.GENERAL_WASTE }
        .sumOf { it.amountKg }

    val totalItemsDiverted = uiState.wasteLogs.sumOf { it.itemsDivertedCount }
    val co2AvoidedKg = totalKgDiverted * 1.6
    val treesEquivalent = (totalKgDiverted / 15.0).coerceAtLeast(0.1)

    val recycledKg = uiState.wasteLogs.filter { it.categoryEnum == WasteCategory.RECYCLABLE }.sumOf { it.amountKg }
    val compostKg = uiState.wasteLogs.filter { it.categoryEnum == WasteCategory.COMPOST }.sumOf { it.amountKg }
    val hazardousKg = uiState.wasteLogs.filter { it.categoryEnum == WasteCategory.HAZARDOUS }.sumOf { it.amountKg }

    val totalKgAll = (totalKgDiverted + uiState.wasteLogs.filter { it.categoryEnum == WasteCategory.GENERAL_WASTE }.sumOf { it.amountKg }).coerceAtLeast(0.1)

    val badges = listOf(
        EcoBadge(
            title = "Zero Waste Starter",
            description = "Logged your first diversion",
            icon = Icons.Default.Eco,
            isUnlocked = uiState.wasteLogs.isNotEmpty(),
            badgeColor = VibrantGreen
        ),
        EcoBadge(
            title = "Recycling Champion",
            description = "Diverted over 5 kg of blue bin materials",
            icon = Icons.Default.Recycling,
            isUnlocked = recycledKg >= 5.0,
            badgeColor = RecycleBlue
        ),
        EcoBadge(
            title = "Compost Master",
            description = "Fed the soil with organic scraps",
            icon = Icons.Default.Park,
            isUnlocked = compostKg >= 3.0,
            badgeColor = CompostGreen
        ),
        EcoBadge(
            title = "Hazardous Guardian",
            description = "Safe disposal of batteries/e-waste",
            icon = Icons.Default.Shield,
            isUnlocked = hazardousKg > 0.0,
            badgeColor = HazardousAmber
        )
    )

    val auditChecklistItems = remember {
        mutableStateListOf(
            "Rinse food containers with cold water before putting in blue bin" to true,
            "Keep batteries in a designated pouch with taped terminals" to true,
            "Collect kitchen food scraps in a countertop compost caddy" to true,
            "Flatten and dry shipping cardboard boxes completely" to false,
            "Carry reusable canvas tote bags on grocery shopping trips" to false,
            "Avoid wishcycling: check guide before throwing plastic cups" to true
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.setShowLogWasteDialog(true) },
                containerColor = VibrantGreen,
                contentColor = Color.White,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.testTag("log_waste_fab")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Log Waste Diversion")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 88.dp)
        ) {
            // Main Summary Card (Vibrant Palette hero card style)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .testTag("eco_impact_summary_card"),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = VibrantSageContainer),
                    border = BorderStroke(1.dp, VibrantBorder)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Household Waste Diversion",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = VibrantDarkGreen
                            )
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(VibrantGreen)
                                    .padding(horizontal = 12.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = "${uiState.wasteLogs.size} logs",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Large KPI metrics row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = String.format(Locale.getDefault(), "%.1f kg", totalKgDiverted),
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Black,
                                    color = VibrantDarkGreen
                                )
                                Text(
                                    text = "Diverted from Landfill",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Medium,
                                    color = VibrantMutedText
                                )
                            }

                            Column {
                                Text(
                                    text = String.format(Locale.getDefault(), "%.1f kg", co2AvoidedKg),
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Black,
                                    color = VibrantGreen
                                )
                                Text(
                                    text = "CO2 Avoided",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Medium,
                                    color = VibrantMutedText
                                )
                            }
                        }
                    }
                }
            }

            // Material Diversion Breakdown
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, VibrantBorderLight)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Diversion by Stream",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = VibrantDarkGreen
                        )
                        Spacer(modifier = Modifier.height(14.dp))

                        // Recyclable Bar
                        DiversionProgressBar(
                            label = "Recyclable (Paper/Plastic/Metal/Glass)",
                            amountKg = recycledKg,
                            fraction = (recycledKg / totalKgAll).toFloat().coerceIn(0f, 1f),
                            color = RecycleBlue
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Compost Bar
                        DiversionProgressBar(
                            label = "Compost & Organic Scraps",
                            amountKg = compostKg,
                            fraction = (compostKg / totalKgAll).toFloat().coerceIn(0f, 1f),
                            color = VibrantGreen
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Hazardous Bar
                        DiversionProgressBar(
                            label = "Hazardous & E-Waste Diverted",
                            amountKg = hazardousKg,
                            fraction = (hazardousKg / totalKgAll).toFloat().coerceIn(0f, 1f),
                            color = HazardousAmber
                        )
                    }
                }
            }

            // Eco Badges & Milestones
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text(
                        text = "Sustainability Milestones",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = VibrantDarkGreen
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(badges) { badge ->
                            Card(
                                shape = RoundedCornerShape(18.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (badge.isUnlocked) Color.White
                                    else VibrantSageLight.copy(alpha = 0.5f)
                                ),
                                border = BorderStroke(
                                    1.dp,
                                    if (badge.isUnlocked) VibrantBorder
                                    else VibrantBorderLight
                                ),
                                modifier = Modifier
                                    .width(150.dp)
                                    .testTag("badge_${badge.title.lowercase().replace(" ", "_")}")
                            ) {
                                Column(
                                    modifier = Modifier.padding(14.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(CircleShape)
                                            .background(if (badge.isUnlocked) badge.badgeColor else VibrantBorderLight),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = badge.icon,
                                            contentDescription = null,
                                            tint = if (badge.isUnlocked) Color.White else VibrantMutedText,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = badge.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = if (badge.isUnlocked) VibrantDarkGreen else VibrantMutedText
                                    )
                                    Text(
                                        text = badge.description,
                                        fontSize = 10.sp,
                                        color = VibrantMutedText,
                                        lineHeight = 14.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Waste Audit Checklist
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, VibrantBorderLight)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Household Waste Audit & Habits",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = VibrantDarkGreen
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        auditChecklistItems.forEachIndexed { idx, itemPair ->
                            val (taskText, isChecked) = itemPair
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = {
                                        auditChecklistItems[idx] = taskText to !isChecked
                                    },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isChecked) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircleOutline,
                                        contentDescription = null,
                                        tint = if (isChecked) VibrantGreen else VibrantMutedText,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = taskText,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (isChecked) VibrantDarkGreen else VibrantMutedText,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }

            // Logged Activity Feed Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Diversion Logs",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = VibrantDarkGreen
                    )
                }
            }

            // Waste Logs
            if (uiState.wasteLogs.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No waste logs recorded yet",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = VibrantDarkGreen
                        )
                        Text(
                            text = "Tap + to log your recycled or composted bags.",
                            style = MaterialTheme.typography.bodySmall,
                            color = VibrantMutedText
                        )
                    }
                }
            } else {
                items(uiState.wasteLogs, key = { it.id }) { log ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                        WasteLogItemCard(
                            log = log,
                            onDelete = { viewModel.deleteHouseholdWasteLog(log) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DiversionProgressBar(
    label: String,
    amountKg: Double,
    fraction: Float,
    color: Color
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = VibrantDarkGreen)
            Text(
                text = String.format(Locale.getDefault(), "%.1f kg", amountKg),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { fraction },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = VibrantSageLight
        )
    }
}

@Composable
fun WasteLogItemCard(
    log: HouseholdWasteLog,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateStr = SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(Date(log.dateTimestamp))

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("waste_log_${log.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = BorderStroke(1.dp, VibrantBorderLight)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryBadge(category = log.categoryEnum, compact = true)

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = String.format(Locale.getDefault(), "%.1f kg (%d items)", log.amountKg, log.itemsDivertedCount),
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = VibrantDarkGreen
                    )
                    Text(
                        text = dateStr,
                        style = MaterialTheme.typography.bodySmall,
                        color = VibrantMutedText
                    )
                }

                if (log.notes.isNotBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = log.notes,
                        style = MaterialTheme.typography.bodySmall,
                        color = VibrantMutedText
                    )
                }
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .size(32.dp)
                    .testTag("delete_log_${log.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = "Delete log",
                    tint = VibrantMutedText.copy(alpha = 0.6f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

