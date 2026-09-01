package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "community_reports")
data class CommunityReport(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val reportType: String = CommunityReportType.OVERFLOWING_BIN.name,
    val location: String,
    val neighborhood: String = "Greenfield Community",
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = ReportStatus.REPORTED.name,
    val upvotes: Int = 1,
    val reporterName: String = "Resident"
) {
    val reportTypeEnum: CommunityReportType
        get() = try {
            CommunityReportType.valueOf(reportType)
        } catch (e: Exception) {
            CommunityReportType.OVERFLOWING_BIN
        }

    val statusEnum: ReportStatus
        get() = try {
            ReportStatus.valueOf(status)
        } catch (e: Exception) {
            ReportStatus.REPORTED
        }
}
