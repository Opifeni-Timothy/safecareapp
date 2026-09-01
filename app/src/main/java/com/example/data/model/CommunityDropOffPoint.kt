package com.example.data.model

data class CommunityDropOffPoint(
    val id: String,
    val name: String,
    val category: WasteCategory,
    val address: String,
    val neighborhood: String,
    val distanceKm: Double,
    val operatingHours: String,
    val acceptedItems: List<String>,
    val contactInfo: String,
    val isOpenNow: Boolean = true,
    val tips: String
)
