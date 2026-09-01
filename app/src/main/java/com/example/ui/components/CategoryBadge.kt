package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.WasteCategory

@Composable
fun getCategoryIcon(category: WasteCategory): ImageVector {
    return when (category) {
        WasteCategory.RECYCLABLE -> Icons.Default.Recycling
        WasteCategory.COMPOST -> Icons.Default.Eco
        WasteCategory.GENERAL_WASTE -> Icons.Default.Delete
        WasteCategory.HAZARDOUS -> Icons.Default.Warning
        WasteCategory.SPECIAL_DROP_OFF -> Icons.Default.LocationOn
    }
}

@Composable
fun CategoryBadge(
    category: WasteCategory,
    modifier: Modifier = Modifier,
    showIcon: Boolean = true,
    compact: Boolean = false
) {
    val bg = category.lightBgColor
    val contentColor = category.color

    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(bg)
            .border(1.dp, category.color.copy(alpha = 0.35f), CircleShape)
            .padding(
                horizontal = if (compact) 8.dp else 12.dp,
                vertical = if (compact) 4.dp else 6.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showIcon) {
            Icon(
                imageVector = getCategoryIcon(category),
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(if (compact) 12.dp else 15.dp)
            )
            Spacer(modifier = Modifier.width(if (compact) 4.dp else 6.dp))
        }
        Text(
            text = category.displayName,
            color = contentColor,
            fontSize = if (compact) 11.sp else 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BinPill(
    category: WasteCategory,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(category.color)
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = category.binColorName,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

