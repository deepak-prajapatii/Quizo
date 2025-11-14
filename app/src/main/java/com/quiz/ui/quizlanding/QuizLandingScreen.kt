package com.quiz.ui.quizlanding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.BubbleChart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun QuizLandingScreen(
    viewModel: QuizLandingViewModel = hiltViewModel(),
    onStartClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier
                .widthIn(max = 420.dp)
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(4.dp))


            Surface(
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(18.dp))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.BubbleChart),
                        contentDescription = "logo",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(44.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Title
            Text(
                text = "Android Quiz",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Subtitle
            Text(
                text = "Test your knowledge with 10 challenging\nquestions about Android development",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 6.dp)
            )

            Spacer(modifier = Modifier.height(22.dp))

            // Info card with rounded corners and larger corner radius to match screenshot
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 160.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    FeatureRow(
                        iconPainter = rememberVectorPainter(Icons.Default.LocalFireDepartment),
                        title = "Streak System",
                        description = "Answer correctly in a row to build your streak and unlock achievements"
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    FeatureRow(
                        iconPainter = rememberVectorPainter(Icons.Default.EmojiEvents),
                        title = "Track Progress",
                        description = "See your score and highest streak at the end of each quiz"
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))


            Button(
                onClick = onStartClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = "Start Quiz", style = MaterialTheme.typography.titleMedium)
            }


            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                FooterStat(
                    number = "10",
                    label = "Questions",
                    modifier = Modifier.width(120.dp)
                )
                FooterStat(
                    number = "4",
                    label = "Choices",
                    modifier = Modifier.width(120.dp)
                )
                FooterStat(
                    number = "~5",
                    label = "Minutes",
                    modifier = Modifier.width(120.dp)
                )
            }
        }
    }
}

@Composable
private fun FeatureRow(
    iconPainter: androidx.compose.ui.graphics.painter.Painter,
    title: String,
    description: String
) {
    Row(verticalAlignment = Alignment.Top) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.size(52.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    painter = iconPainter,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun FooterStat(
    number: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Text(
            text = number,
            fontWeight = FontWeight.Black,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
