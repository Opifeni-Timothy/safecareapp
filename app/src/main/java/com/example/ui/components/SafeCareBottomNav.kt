package com.example.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.VibrantBorderLight
import com.example.ui.theme.VibrantDarkGreen
import com.example.ui.theme.VibrantGreen
import com.example.ui.theme.VibrantMutedText
import com.example.ui.theme.VibrantSageContainer
import com.example.ui.theme.VibrantSageLight
import com.example.ui.viewmodel.SafeCareTab

@Composable
fun SafeCareBottomNav(
    selectedTab: SafeCareTab,
    onTabSelected: (SafeCareTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = VibrantBorderLight,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            }
            .testTag("bottom_nav_bar"),
        containerColor = VibrantSageLight,
        tonalElevation = 0.dp
    ) {
        val tabs = listOf(
            SafeCareTab.SCHEDULES to (Icons.Filled.CalendarMonth to Icons.Outlined.CalendarMonth),
            SafeCareTab.GUIDE to (Icons.Filled.MenuBook to Icons.Outlined.MenuBook),
            SafeCareTab.COMMUNITY to (Icons.Filled.Groups to Icons.Outlined.Groups),
            SafeCareTab.TRACKER to (Icons.Filled.Eco to Icons.Outlined.Eco)
        )

        tabs.forEach { (tab, icons) ->
            val isSelected = selectedTab == tab
            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) icons.first else icons.second,
                        contentDescription = tab.title
                    )
                },
                label = {
                    Text(
                        text = tab.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = VibrantDarkGreen,
                    selectedTextColor = VibrantDarkGreen,
                    indicatorColor = VibrantSageContainer,
                    unselectedIconColor = VibrantMutedText,
                    unselectedTextColor = VibrantMutedText
                ),
                modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
            )
        }
    }
}

