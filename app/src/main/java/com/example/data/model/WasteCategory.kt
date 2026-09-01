package com.example.data.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.CompostGreen
import com.example.ui.theme.CompostGreenLight
import com.example.ui.theme.HazardousAmber
import com.example.ui.theme.HazardousAmberLight
import com.example.ui.theme.LandfillSlate
import com.example.ui.theme.LandfillSlateLight
import com.example.ui.theme.RecycleBlue
import com.example.ui.theme.RecycleBlueLight
import com.example.ui.theme.SpecialPurple
import com.example.ui.theme.SpecialPurpleLight

enum class WasteCategory(
    val displayName: String,
    val binColorName: String,
    val color: Color,
    val lightBgColor: Color,
    val iconName: String,
    val description: String
) {
    RECYCLABLE(
        displayName = "Recyclable",
        binColorName = "Blue Bin",
        color = RecycleBlue,
        lightBgColor = RecycleBlueLight,
        iconName = "recycling",
        description = "Clean paper, cardboard, rigid plastics #1-#5, metal cans, glass jars"
    ),
    COMPOST(
        displayName = "Compost / Organic",
        binColorName = "Green Bin",
        color = CompostGreen,
        lightBgColor = CompostGreenLight,
        iconName = "eco",
        description = "Food scraps, fruit & vegetable peels, coffee grounds, garden clippings"
    ),
    GENERAL_WASTE(
        displayName = "General Waste",
        binColorName = "Black / Grey Bin",
        color = LandfillSlate,
        lightBgColor = LandfillSlateLight,
        iconName = "delete_outline",
        description = "Non-recyclable plastics, contaminated packaging, broken ceramics, wrappers"
    ),
    HAZARDOUS(
        displayName = "Hazardous / E-Waste",
        binColorName = "Orange / Red Box",
        color = HazardousAmber,
        lightBgColor = HazardousAmberLight,
        iconName = "warning",
        description = "Batteries, electronics, paint, chemicals, fluorescent tubes, pharmaceuticals"
    ),
    SPECIAL_DROP_OFF(
        displayName = "Community Drop-Off",
        binColorName = "Purple Depot",
        color = SpecialPurple,
        lightBgColor = SpecialPurpleLight,
        iconName = "location_on",
        description = "Large bulk items, textiles, appliances, motor oil, certified scrap hubs"
    )
}

enum class ScheduleFrequency(val label: String) {
    WEEKLY("Weekly"),
    BI_WEEKLY("Every 2 Weeks"),
    MONTHLY("Monthly"),
    CUSTOM("Custom / As Needed")
}

enum class CommunityReportType(val label: String) {
    OVERFLOWING_BIN("Overflowing Community Bin"),
    ILLEGAL_DUMPING("Illegal Dumping Spot"),
    MISSED_PICKUP("Missed Collection Report"),
    DAMAGED_BIN("Damaged Public Bin"),
    CLEANUP_DRIVE("Community Cleanup Suggestion")
}

enum class ReportStatus(val label: String) {
    REPORTED("Reported"),
    IN_PROGRESS("In Progress"),
    RESOLVED("Resolved")
}
