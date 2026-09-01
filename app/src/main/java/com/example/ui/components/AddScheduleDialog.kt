package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.ScheduleFrequency
import com.example.data.model.WasteCategory
import com.example.data.model.WasteSchedule

@Composable
fun AddScheduleDialog(
    onDismiss: () -> Unit,
    onConfirm: (WasteSchedule) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(WasteCategory.RECYCLABLE) }
    var selectedDay by remember { mutableStateOf("Monday") }
    var timeOfDay by remember { mutableStateOf("07:00 AM") }
    var zoneOrAddress by remember { mutableStateOf("Home / Residential") }
    var instructions by remember { mutableStateOf("") }
    var reminderEnabled by remember { mutableStateOf(true) }
    var isCommunity by remember { mutableStateOf(false) }

    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(20.dp)),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Add Collection Schedule",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_add_schedule_dialog")
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Schedule Title Input
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Schedule Title (e.g., Paper & Plastic Pickup)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("schedule_title_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Category Selection Chips
                Text(
                    text = "Waste Type & Bin",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    WasteCategory.entries.chunked(2).forEach { rowCategories ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            rowCategories.forEach { category ->
                                val isSelected = selectedCategory == category
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(
                                            if (isSelected) category.color.copy(alpha = 0.2f)
                                            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                        )
                                        .border(
                                            width = if (isSelected) 2.dp else 1.dp,
                                            color = if (isSelected) category.color else Color.Transparent,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .clickable { selectedCategory = category }
                                        .padding(vertical = 8.dp, horizontal = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = category.displayName,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) category.color else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Day Selection
                Text(
                    text = "Collection Day",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    daysOfWeek.forEach { day ->
                        val isSelected = selectedDay == day
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surfaceVariant
                                )
                                .clickable { selectedDay = day }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day.take(3),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Time of Day
                OutlinedTextField(
                    value = timeOfDay,
                    onValueChange = { timeOfDay = it },
                    label = { Text("Time (e.g. 07:00 AM)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("schedule_time_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Zone or Address
                OutlinedTextField(
                    value = zoneOrAddress,
                    onValueChange = { zoneOrAddress = it },
                    label = { Text("Zone / Neighborhood or Address") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("schedule_zone_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Instructions
                OutlinedTextField(
                    value = instructions,
                    onValueChange = { instructions = it },
                    label = { Text("Sorting / Placement Instructions") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("schedule_instructions_input"),
                    minLines = 2
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Toggle Reminders
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Enable Pickup Reminder",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Receive alert the evening before",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = reminderEnabled,
                        onCheckedChange = { reminderEnabled = it }
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            if (title.isNotBlank()) {
                                onConfirm(
                                    WasteSchedule(
                                        title = title.trim(),
                                        category = selectedCategory.name,
                                        dayOfWeek = selectedDay,
                                        timeOfDay = timeOfDay.trim().ifBlank { "07:00 AM" },
                                        zoneOrAddress = zoneOrAddress.trim().ifBlank { "Home" },
                                        instructions = instructions.trim().ifBlank { "Place bins at curbside on time." },
                                        reminderEnabled = reminderEnabled,
                                        isCommunitySchedule = isCommunity
                                    )
                                )
                            }
                        },
                        enabled = title.isNotBlank(),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("save_schedule_btn"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Save Schedule")
                    }
                }
            }
        }
    }
}
