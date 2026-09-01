package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "household_waste_logs")
data class HouseholdWasteLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dateTimestamp: Long = System.currentTimeMillis(),
    val category: String, // Maps to WasteCategory.name
    val amountKg: Double,
    val itemsDivertedCount: Int = 1,
    val notes: String = ""
) {
    val categoryEnum: WasteCategory
        get() = try {
            WasteCategory.valueOf(category)
        } catch (e: Exception) {
            WasteCategory.RECYCLABLE
        }
}
