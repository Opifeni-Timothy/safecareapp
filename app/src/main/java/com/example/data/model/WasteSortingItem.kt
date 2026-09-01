package com.example.data.model

data class WasteSortingItem(
    val id: String,
    val name: String,
    val category: WasteCategory,
    val subcategory: String,
    val recyclingCode: String? = null,
    val preparationSteps: List<String>,
    val commonMistakes: List<String>,
    val disposalGuideline: String,
    val environmentalImpact: String,
    val isPopular: Boolean = false
)
