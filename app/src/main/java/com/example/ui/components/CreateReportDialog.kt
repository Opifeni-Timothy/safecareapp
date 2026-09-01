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
import com.example.data.model.CommunityReport
import com.example.data.model.CommunityReportType
import com.example.data.model.ReportStatus

@Composable
fun CreateReportDialog(
    onDismiss: () -> Unit,
    onConfirm: (CommunityReport) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(CommunityReportType.OVERFLOWING_BIN) }
    var location by remember { mutableStateOf("") }
    var neighborhood by remember { mutableStateOf("Greenfield Community") }
    var description by remember { mutableStateOf("") }
    var reporterName by remember { mutableStateOf("Resident") }

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
                        text = "Report Community Issue",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_create_report_dialog")
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Issue Type selector
                Text(
                    text = "Report Type",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    CommunityReportType.entries.forEach { type ->
                        val isSelected = selectedType == type
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                )
                                .border(
                                    width = if (isSelected) 1.5.dp else 1.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable { selectedType = type }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = type.label,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Title
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Brief Title (e.g. Broken bin at Elm St park)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("report_title_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Location
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Exact Location / Street / Landmark") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("report_location_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Description
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description & Details") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("report_description_input"),
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Reporter Name
                OutlinedTextField(
                    value = reporterName,
                    onValueChange = { reporterName = it },
                    label = { Text("Your Name or 'Anonymous Resident'") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("report_author_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Actions
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
                            if (title.isNotBlank() && location.isNotBlank()) {
                                onConfirm(
                                    CommunityReport(
                                        title = title.trim(),
                                        description = description.trim().ifBlank { "Community waste report." },
                                        reportType = selectedType.name,
                                        location = location.trim(),
                                        neighborhood = neighborhood.trim(),
                                        reporterName = reporterName.trim().ifBlank { "Resident" },
                                        status = ReportStatus.REPORTED.name,
                                        upvotes = 1
                                    )
                                )
                            }
                        },
                        enabled = title.isNotBlank() && location.isNotBlank(),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("submit_report_btn"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Post Report")
                    }
                }
            }
        }
    }
}
