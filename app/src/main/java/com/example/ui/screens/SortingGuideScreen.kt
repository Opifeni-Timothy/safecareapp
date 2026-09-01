package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.WasteCategory
import com.example.ui.components.SortingItemCard
import com.example.ui.components.getCategoryIcon
import com.example.ui.theme.VibrantBorder
import com.example.ui.theme.VibrantBorderLight
import com.example.ui.theme.VibrantBorderMuted
import com.example.ui.theme.VibrantDarkGreen
import com.example.ui.theme.VibrantGreen
import com.example.ui.theme.VibrantMutedText
import com.example.ui.theme.VibrantSageContainer
import com.example.ui.theme.VibrantSageLight
import com.example.ui.viewmodel.SafeCareUiState
import com.example.ui.viewmodel.SafeCareViewModel

@Composable
fun SortingGuideScreen(
    uiState: SafeCareUiState,
    viewModel: SafeCareViewModel,
    modifier: Modifier = Modifier
) {
    val filteredItems = uiState.sortingItems.filter { item ->
        val matchesCategory = uiState.selectedGuideCategory == null || item.category == uiState.selectedGuideCategory
        val query = uiState.searchQuery.trim().lowercase()
        val matchesQuery = query.isEmpty() ||
                item.name.lowercase().contains(query) ||
                item.subcategory.lowercase().contains(query) ||
                (item.recyclingCode?.lowercase()?.contains(query) == true) ||
                item.disposalGuideline.lowercase().contains(query)
        matchesCategory && matchesQuery
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 88.dp)
    ) {
        // Search Bar
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = { viewModel.setSearchQuery(it) },
                    placeholder = { Text("Search items (e.g. pizza box, battery, PET...)") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = VibrantGreen
                        )
                    },
                    trailingIcon = {
                        if (uiState.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear search", tint = VibrantMutedText)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("sorting_search_input"),
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = VibrantGreen,
                        unfocusedBorderColor = VibrantBorderLight,
                        focusedTextColor = VibrantDarkGreen,
                        unfocusedTextColor = VibrantDarkGreen
                    ),
                    singleLine = true
                )
            }
        }

        // Quiz Banner Promo Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .testTag("quiz_promo_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = VibrantSageContainer),
                border = BorderStroke(1.dp, VibrantBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(VibrantGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Test Your Sorting IQ",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = VibrantDarkGreen
                        )
                        Text(
                            text = "Quick 5-question family & community quiz",
                            style = MaterialTheme.typography.bodySmall,
                            color = VibrantMutedText
                        )
                    }
                    Button(
                        onClick = { viewModel.startQuiz() },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = VibrantGreen),
                        modifier = Modifier.testTag("start_quiz_btn")
                    ) {
                        Text("Start", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }

        // Category Filter Chips
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    text = "Browse by Bin & Category",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = VibrantDarkGreen
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterChip(
                            selected = uiState.selectedGuideCategory == null,
                            onClick = { viewModel.selectGuideCategory(null) },
                            label = { Text("All Items") },
                            shape = RoundedCornerShape(20.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = VibrantSageContainer,
                                selectedLabelColor = VibrantDarkGreen,
                                containerColor = Color.White,
                                labelColor = VibrantMutedText
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = uiState.selectedGuideCategory == null,
                                borderColor = if (uiState.selectedGuideCategory == null) VibrantBorder else VibrantBorderLight
                            ),
                            modifier = Modifier.testTag("filter_all_categories")
                        )
                    }
                    items(WasteCategory.entries) { category ->
                        val isSelected = uiState.selectedGuideCategory == category
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                viewModel.selectGuideCategory(if (isSelected) null else category)
                            },
                            label = { Text(category.displayName) },
                            leadingIcon = {
                                Icon(
                                    imageVector = getCategoryIcon(category),
                                    contentDescription = null,
                                    tint = if (isSelected) VibrantGreen else category.color,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            shape = RoundedCornerShape(20.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = VibrantSageContainer,
                                selectedLabelColor = VibrantDarkGreen,
                                containerColor = Color.White,
                                labelColor = VibrantMutedText
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = if (isSelected) VibrantBorder else VibrantBorderLight
                            ),
                            modifier = Modifier.testTag("filter_cat_${category.name.lowercase()}")
                        )
                    }
                }
            }
        }

        // Common Plastics / Resin Codes Quick Legend
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = VibrantSageLight),
                border = BorderStroke(1.dp, VibrantBorderLight)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Plastic Resin Code Quick Reference",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = VibrantDarkGreen
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("#1 PETE", "#2 HDPE", "#4 LDPE", "#5 PP", "#6 PS").forEach { code ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color.White,
                                border = BorderStroke(1.dp, VibrantBorderLight),
                                modifier = Modifier
                                    .clickable { viewModel.setSearchQuery(code.substringBefore(" ")) }
                                    .padding(horizontal = 2.dp)
                            ) {
                                Text(
                                    text = code,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VibrantDarkGreen,
                                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Items Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sorting Directory (${filteredItems.size})",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = VibrantDarkGreen
                )
            }
        }

        // Items List
        if (filteredItems.isEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = VibrantMutedText.copy(alpha = 0.5f),
                        modifier = Modifier.size(44.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "No matching items found",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = VibrantDarkGreen
                    )
                    Text(
                        text = "Try searching for generic terms like 'plastic', 'box', or 'battery'.",
                        style = MaterialTheme.typography.bodySmall,
                        color = VibrantMutedText
                    )
                }
            }
        } else {
            items(filteredItems, key = { it.id }) { item ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                    SortingItemCard(
                        item = item,
                        onClick = { viewModel.selectSortingItem(item) }
                    )
                }
            }
        }
    }
}

