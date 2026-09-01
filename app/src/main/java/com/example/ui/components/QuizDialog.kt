package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.repository.WasteGuideDataSource
import com.example.ui.theme.CompostGreen

@Composable
fun QuizDialog(
    questions: List<WasteGuideDataSource.QuizQuestion>,
    currentIndex: Int,
    selectedIndex: Int?,
    score: Int,
    isFinished: Boolean,
    onAnswerSelected: (Int) -> Unit,
    onNext: () -> Unit,
    onClose: () -> Unit,
    onRestart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(20.dp)),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Eco,
                            contentDescription = null,
                            tint = CompostGreen,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Sorting Master Quiz",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.testTag("close_quiz_btn")
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (isFinished) {
                    // Result Summary Screen
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = "Quiz Score",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(38.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Quiz Completed!",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "You scored $score out of ${questions.size}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        val feedbackMessage = when {
                            score == questions.size -> "Flawless! You're a verified Safe Care sorting champion."
                            score >= 3 -> "Great job! You know your household recyclables and compost well."
                            else -> "Good practice! Review the guidelines to keep improving your waste diversion."
                        }

                        Text(
                            text = feedbackMessage,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 12.dp),
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = onRestart,
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("restart_quiz_btn"),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Try Again")
                            }

                            Button(
                                onClick = onClose,
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("finish_quiz_btn"),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                            ) {
                                Text("Close")
                            }
                        }
                    }
                } else if (currentIndex < questions.size) {
                    val question = questions[currentIndex]

                    // Progress Indicator
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Question ${currentIndex + 1} of ${questions.size}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Score: $score",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = question.question,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Options
                    question.options.forEachIndexed { optIndex, optionText ->
                        val isSelected = selectedIndex == optIndex
                        val hasAnswered = selectedIndex != null
                        val isCorrectOption = optIndex == question.correctIndex

                        val containerColor = when {
                            !hasAnswered -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                            isCorrectOption -> CompostGreen.copy(alpha = 0.18f)
                            isSelected && !isCorrectOption -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
                            else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)
                        }

                        val borderColor = when {
                            !hasAnswered -> if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
                            isCorrectOption -> CompostGreen
                            isSelected && !isCorrectOption -> MaterialTheme.colorScheme.error
                            else -> Color.Transparent
                        }

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.5.dp, borderColor, RoundedCornerShape(12.dp))
                                .clickable(enabled = !hasAnswered) {
                                    onAnswerSelected(optIndex)
                                }
                                .testTag("quiz_option_$optIndex"),
                            shape = RoundedCornerShape(12.dp),
                            color = containerColor
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (hasAnswered) {
                                    Icon(
                                        imageVector = if (isCorrectOption) Icons.Default.CheckCircle else if (isSelected) Icons.Default.HighlightOff else Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = if (isCorrectOption) CompostGreen else if (isSelected) MaterialTheme.colorScheme.error else Color.Transparent,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                }

                                Text(
                                    text = optionText,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (isSelected || isCorrectOption && hasAnswered) FontWeight.Bold else FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }

                    // Explanation Callout
                    if (selectedIndex != null) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "Explanation",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = question.explanation,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 18.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = onNext,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("next_quiz_question_btn"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(
                                text = if (currentIndex + 1 < questions.size) "Next Question" else "See Results",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
