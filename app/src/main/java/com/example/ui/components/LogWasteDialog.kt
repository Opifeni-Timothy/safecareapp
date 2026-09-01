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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.HouseholdWasteLog
import com.example.data.model.WasteCategory

@Composable
fun LogWasteDialog(
    onDismiss: () -> Unit,
    onConfirm: (HouseholdWasteLog) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf(WasteCategory.RECYCLABLE) }
    var weightInput by remember { mutableStateOf("2.0") }
    var itemCountInput by remember { mutableStateOf("5") }
    var notes by remember { mutableStateOf("") }

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
                        text = "Log Household Waste",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_log_waste_dialog")
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Category selector
                Text(
                    text = "Waste Category",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    WasteCategory.entries.chunked(2).forEach { rowCats ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            rowCats.forEach { cat ->
                                val isSelected = selectedCategory == cat
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(
                                            if (isSelected) cat.color.copy(alpha = 0.2f)
                                            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                        )
                                        .border(
                                            width = if (isSelected) 2.dp else 1.dp,
                                            color = if (isSelected) cat.color else Color.Transparent,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .clickable { selectedCategory = cat }
                                        .padding(vertical = 8.dp, horizontal = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = cat.displayName,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) cat.color else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Weight in Kg
                OutlinedTextField(
                    value = weightInput,
                    onValueChange = { weightInput = it },
                    label = { Text("Estimated Weight (kg)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("waste_weight_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Items Count
                OutlinedTextField(
                    value = itemCountInput,
                    onValueChange = { itemCountInput = it },
                    label = { Text("Estimated Items Count (e.g. 8 bottles, 1 box)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("waste_items_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Notes / Description
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Details / Materials sorted") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("waste_notes_input"),
                    minLines = 2
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
                            val kg = weightInput.toDoubleOrNull() ?: 1.0
                            val count = itemCountInput.toIntOrNull() ?: 1
                            onConfirm(
                                HouseholdWasteLog(
                                    dateTimestamp = System.currentTimeMillis(),
                                    category = selectedCategory.name,
                                    amountKg = kg,
                                    itemsDivertedCount = count,
                                    notes = notes.trim().ifBlank { "${selectedCategory.displayName} sorted" }
                                )
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("submit_waste_log_btn"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Record Log")
                    }
                }
            }
        }
    }
}
