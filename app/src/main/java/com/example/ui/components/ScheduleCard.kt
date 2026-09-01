package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.WasteSchedule
import com.example.ui.theme.VibrantBorderLight
import com.example.ui.theme.VibrantBorderMuted
import com.example.ui.theme.VibrantDarkGreen
import com.example.ui.theme.VibrantGreen
import com.example.ui.theme.VibrantMutedText
import com.example.ui.theme.VibrantSageContainer
import com.example.ui.theme.VibrantSageLight

@Composable
fun ScheduleCard(
    schedule: WasteSchedule,
    onToggleCompletion: () -> Unit,
    onToggleReminder: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val category = schedule.categoryEnum
    val isCompleted = schedule.isCompletedThisCycle

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("schedule_card_${schedule.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) {
                VibrantSageLight.copy(alpha = 0.6f)
            } else {
                Color.White
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isCompleted) 0.dp else 1.dp),
        border = BorderStroke(
            1.dp,
            if (isCompleted) VibrantBorderLight else VibrantBorderLight
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Row: Category Badge + Bin Pill + Delete Action
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    CategoryBadge(category = category, compact = true)
                    Spacer(modifier = Modifier.width(8.dp))
                    BinPill(category = category)
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(32.dp)
                        .testTag("delete_schedule_${schedule.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Delete schedule",
                        tint = VibrantMutedText.copy(alpha = 0.6f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Main Title and completion toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onToggleCompletion,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("toggle_complete_${schedule.id}")
                ) {
                    Icon(
                        imageVector = if (isCompleted) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircleOutline,
                        contentDescription = if (isCompleted) "Completed" else "Pending",
                        tint = if (isCompleted) VibrantGreen else VibrantMutedText,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = schedule.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isCompleted) VibrantMutedText else VibrantDarkGreen,
                        textDecoration = if (isCompleted) TextDecoration.LineThrough else TextDecoration.None
                    )
                    Text(
                        text = "${schedule.dayOfWeek} • ${schedule.timeOfDay} (${schedule.frequencyEnum.label})",
                        style = MaterialTheme.typography.bodyMedium,
                        color = VibrantGreen,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Zone / Address Tag
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = VibrantMutedText,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = schedule.zoneOrAddress,
                    style = MaterialTheme.typography.bodySmall,
                    color = VibrantMutedText
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = VibrantBorderLight)
            Spacer(modifier = Modifier.height(10.dp))

            // Footer controls: Reminder Switch & Expandable Guidelines
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onToggleReminder() }
                ) {
                    Icon(
                        imageVector = if (schedule.reminderEnabled) Icons.Filled.Notifications else Icons.Filled.NotificationsOff,
                        contentDescription = "Reminder status",
                        tint = if (schedule.reminderEnabled) VibrantGreen else VibrantMutedText,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (schedule.reminderEnabled) "Reminder active" else "Reminder off",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (schedule.reminderEnabled) VibrantDarkGreen else VibrantMutedText,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { expanded = !expanded }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (expanded) "Hide tips" else "Sorting tips",
                        style = MaterialTheme.typography.labelMedium,
                        color = VibrantGreen,
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = null,
                        tint = VibrantGreen,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(category.lightBgColor)
                        .padding(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = category.color,
                            modifier = Modifier
                                .size(16.dp)
                                .padding(top = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = schedule.instructions,
                            style = MaterialTheme.typography.bodySmall,
                            color = VibrantDarkGreen,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}

