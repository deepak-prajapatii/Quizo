package com.quiz.ui.result

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun QuizResultScreen(
    viewModel: QuizResultViewModel = hiltViewModel(),
    onRestartQuiz: () -> Unit,
    backToHome: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    // Loading state
    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // Map viewmodel state to UI params
    val correctCount = state.correct
    val totalQuestions = state.totalQuestions
    val bestStreak = state.bestStreak

    // Call the visual UI
    QuizResultScreen(
        correctCount = correctCount,
        totalQuestions = totalQuestions,
        bestStreak = bestStreak,
        onRestart = onRestartQuiz,
        onBack = backToHome
    )
}

@Composable
fun QuizResultScreen(
    correctCount: Int,
    totalQuestions: Int,
    bestStreak: Int,
    onRestart: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val percent = if (totalQuestions > 0) (correctCount * 100 / totalQuestions) else 0

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 420.dp)
                .padding(vertical = 12.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(28.dp),
                    clip = false
                ),
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp, vertical = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Trophy Icon Circle
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.size(92.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = "trophy",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(44.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Congratulations!",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )


                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Keep practicing! 📚",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "You've completed the quiz. Here's your performance summary:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(22.dp))

                // Score + Streak tiles
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ResultStatSurface(
                        title = "Score",
                        value = "$percent%",
                        subtitle = "$correctCount/$totalQuestions correct",
                        modifier = Modifier.weight(1f)
                    )

                    ResultStatSurface(
                        title = "Best Streak",
                        value = "$bestStreak",
                        subtitle = "in a row",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                // Restart Button
                Button(
                    onClick = onRestart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Restart Quiz", style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Back to Home (Outlined Surface Button)
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable { onBack() },
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "home",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Back to Home",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

@Composable
private fun ResultStatSurface(
    title: String,
    value: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.displaySmall.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )
            Text(
                text = value,
                style = MaterialTheme.typography.displaySmall.copy(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
