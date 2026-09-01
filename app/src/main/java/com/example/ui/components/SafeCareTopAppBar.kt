package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.VibrantDarkGreen
import com.example.ui.theme.VibrantGreen
import com.example.ui.theme.VibrantMutedText
import com.example.ui.theme.VibrantSageContainer
import com.example.ui.viewmodel.SafeCareTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SafeCareTopAppBar(
    currentTab: SafeCareTab,
    onOpenQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(VibrantGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Eco,
                        contentDescription = "Safe Care Logo",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Safe Care",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = VibrantDarkGreen,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = when (currentTab) {
                            SafeCareTab.SCHEDULES -> "Springfield Neighborhood"
                            SafeCareTab.GUIDE -> "Smart Sorting & Recycling Guide"
                            SafeCareTab.COMMUNITY -> "Community Hub & Cleanup Points"
                            SafeCareTab.TRACKER -> "Eco Impact & Diversion Score"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = VibrantMutedText
                    )
                }

                // Neighborhood Eco Points Badge (matches design HTML: rounded-full bg-[#D7E8CD] border-2 border-white shadow-sm)
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .shadow(2.dp, CircleShape)
                        .clip(CircleShape)
                        .background(VibrantSageContainer)
                        .border(2.dp, Color.White, CircleShape)
                        .clickable { onOpenQuiz() }
                        .testTag("neighborhood_eco_badge"),
                    contentAlignment = Alignment.Center
                ) {
                    if (currentTab == SafeCareTab.GUIDE) {
                        Icon(
                            imageVector = Icons.Default.Quiz,
                            contentDescription = "Sorting Quiz",
                            tint = VibrantGreen,
                            modifier = Modifier.size(22.dp)
                        )
                    } else {
                        Text(
                            text = "120",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = VibrantGreen
                        )
                    }
                }
            }
        }
    }
}

