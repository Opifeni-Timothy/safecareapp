package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "waste_schedules")
data class WasteSchedule(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val category: String, // Maps to WasteCategory.name
    val dayOfWeek: String, // e.g., "Monday", "Wednesday", "Friday"
    val frequency: String = ScheduleFrequency.WEEKLY.name,
    val timeOfDay: String = "07:00 AM",
    val reminderEnabled: Boolean = true,
    val reminderMinutesBefore: Int = 720, // 12 hours before (evening before)
    val zoneOrAddress: String = "Residential Zone 1",
    val instructions: String = "Place bins at curbside before 6:30 AM with lids closed.",
    val isCompletedThisCycle: Boolean = false,
    val isCommunitySchedule: Boolean = false
) {
    val categoryEnum: WasteCategory
        get() = try {
            WasteCategory.valueOf(category)
        } catch (e: Exception) {
            WasteCategory.GENERAL_WASTE
        }

    val frequencyEnum: ScheduleFrequency
        get() = try {
            ScheduleFrequency.valueOf(frequency)
        } catch (e: Exception) {
            ScheduleFrequency.WEEKLY
        }
}
