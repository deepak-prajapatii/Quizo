package com.quiz.ui.quizquestion

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.quiz.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import kotlinx.coroutines.delay


@Composable
fun QuizQuestionScreen(
    viewModel: QuizQuestionViewModel = hiltViewModel(),
    showResult: (Int, Int, Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when(event) {
                QuizQuestionUIEvent.ShowResult -> showResult(
                    state.score, state.questions.size,state.bestStreak
                )
            }
        }

    }

    // Loading state
    if (state.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val current = state.questions.getOrNull(state.currentQuestionIndex)
    if (current == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No questions available")
        }
        return
    }

    // Show the question card UI, wiring callbacks
    QuizQuestionCard(
        questionIndex = state.currentQuestionIndex + 1,
        totalQuestions = state.questions.size,
        question = current.question,
        choices = current.options,
        selectedIndex = state.selectedOptionIndex,
        isAnswered = state.isAnswered,
        isCorrect = state.isCorrect,
        currentStreak = state.currentStreak,
        onChoiceSelected = { idx -> viewModel.onChoiceSelected(idx) },
        onSkip = viewModel::onSkip
    )
}

@Composable
fun QuizQuestionCard(
    questionIndex: Int,
    totalQuestions: Int,
    question: String,
    choices: List<String>,
    selectedIndex: Int?,
    isAnswered: Boolean,
    isCorrect: Boolean?,
    currentStreak: Int,
    onChoiceSelected: (Int) -> Unit,
    onSkip: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 420.dp)
                .padding(vertical = 12.dp),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 18.dp)
            ) {
                // Top row: title + streak chip
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Quiz",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.weight(1f))

//                    // Streak chip
//                    Surface(
//                        shape = RoundedCornerShape(20.dp),
//                        color = MaterialTheme.colorScheme.surfaceVariant
//                    ) {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.LocalFireDepartment,
//                                contentDescription = "streak",
//                                tint = MaterialTheme.colorScheme.primary,
//                                modifier = Modifier.size(18.dp)
//                            )
//                            Spacer(modifier = Modifier.width(8.dp))
//                            Text(
//                                text = "$currentStreak",
//                                style = MaterialTheme.typography.bodySmall,
//                                color = MaterialTheme.colorScheme.onSurface
//                            )
//                        }
//                    }

                    StreakRow(currentStreak = currentStreak, threshold = 2, lottieResId = R.raw.fire_streak)
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Progress label
                Text(
                    text = "Question $questionIndex of $totalQuestions",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Thin progress rail (smaller)
                LinearProgressIndicator(
                    progress = (questionIndex.toFloat() / totalQuestions.toFloat()).coerceIn(0f, 1f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f)
                )

                Spacer(modifier = Modifier.height(22.dp))


                Text(
                    text = question,
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    choices.forEachIndexed { idx, choice ->
                        val isSelected = selectedIndex == idx
                        val showCorrect = isAnswered && isCorrect == true && isSelected
                        val showWrong = isAnswered && isSelected && isCorrect == false
                        ChoiceItem(
                            text = choice,
                            isSelected = isSelected,
                            showCorrect = showCorrect,
                            showWrong = showWrong,
                            onClick = { onChoiceSelected(idx) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Skip action centered
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Skip",
                        modifier = Modifier
                            .clickable { onSkip() }
                            .padding(vertical = 8.dp),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
private fun ChoiceItem(
    text: String,
    isSelected: Boolean,
    showCorrect: Boolean,
    showWrong: Boolean,
    onClick: () -> Unit
) {
    // Determine colors based on state
    val primary = MaterialTheme.colorScheme.primary
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    val defaultBg = MaterialTheme.colorScheme.surfaceVariant
    val outline = MaterialTheme.colorScheme.outline
    val error = MaterialTheme.colorScheme.error
    val contentColor = MaterialTheme.colorScheme.onSurface

    val containerColor = when {
        showCorrect -> primary
        showWrong -> MaterialTheme.colorScheme.surfaceVariant // keep bg same but outline red
        isSelected -> primary
        else -> defaultBg
    }

    val textColor = when {
        showCorrect -> onPrimary
        isSelected -> onPrimary
        showWrong -> contentColor
        else -> contentColor
    }

    val borderColor = when {
        showCorrect -> primary
        showWrong -> error
        isSelected -> primary
        else -> outline
    }

    Surface(
        color = containerColor,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(onClick = onClick),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        // apply border and padding directly
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(BorderStroke(1.dp, borderColor), shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun StreakRow(
    currentStreak: Int,
    modifier: Modifier = Modifier,
    threshold: Int = 3,
    lottieResId: Int? = null,
    iconSize: Dp = 18.dp
) {
    // track previous streak to detect crossing the threshold
    var prevStreak by remember { mutableIntStateOf(currentStreak) }
    val justReachedThreshold = remember(currentStreak, prevStreak) {
        (prevStreak < threshold) && (currentStreak >= threshold)
    }
    LaunchedEffect(currentStreak) { prevStreak = currentStreak }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        val showLottie = currentStreak >= threshold
        val transition = updateTransition(targetState = showLottie, label = "streakTransition")

        val lottieAlpha by transition.animateFloat(
            transitionSpec = { tween(durationMillis = 300, easing = FastOutSlowInEasing) },
            label = "lottieAlpha"
        ) { visible -> if (visible) 1f else 0f }

        val lottieScale by transition.animateFloat(
            transitionSpec = { spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow) },
            label = "lottieScale"
        ) { visible -> if (visible) 1.12f else 0.95f }

        val iconAlpha by transition.animateFloat(
            transitionSpec = { tween(durationMillis = 240, easing = LinearOutSlowInEasing) },
            label = "iconAlpha"
        ) { visible -> if (visible) 0f else 1f }

        val iconScale by transition.animateFloat(
            transitionSpec = { tween(durationMillis = 220, easing = LinearOutSlowInEasing) },
            label = "iconScale"
        ) { visible -> if (visible) 0.92f else 1f }

        // Shared box for perfect centering and identical bounds
        Box(
            modifier = Modifier.size(iconSize * 1.6f),
            contentAlignment = Alignment.Center
        ) {
            // --- Icon (drawn always but alpha/scaled out when Lottie visible) ---
            Icon(
                imageVector = Icons.Default.LocalFireDepartment,
                contentDescription = "streak",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(iconSize)
                    .scale(iconScale)
                    .alpha(iconAlpha)
                    .semantics { contentDescription = "streak-icon" }
            )

            // --- Lottie (only used when lottieResId provided) ---
            if (lottieResId != null) {
                // remember the composition spec and composition
                val compositionSpec = remember(lottieResId) { LottieCompositionSpec.RawRes(lottieResId) }
                val composition by rememberLottieComposition(spec = compositionSpec)

                // get composition duration (ms) if available to time the burst
                val compositionDurationMs = composition?.duration?.toLong() ?: 700L

                // control iterations and speed: burst once when justReachedThreshold
                var iterations by remember { mutableIntStateOf(LottieConstants.IterateForever) }
                var speed by remember { mutableFloatStateOf(1f) }

                LaunchedEffect(justReachedThreshold, compositionDurationMs, showLottie) {
                    if (justReachedThreshold && showLottie) {
                        // play the "burst" once at higher speed
                        iterations = 1
                        speed = 1.8f
                        delay(compositionDurationMs.coerceAtLeast(300L))
                        iterations = LottieConstants.IterateForever
                        speed = 0.95f
                    } else if (!showLottie) {
                        // hidden -> reset
                        iterations = LottieConstants.IterateForever
                        speed = 1f
                    } else {
                        // already visible (not a fresh crossing) -> gentle loop
                        iterations = LottieConstants.IterateForever
                        speed = 0.95f
                    }
                }

                val progress by animateLottieCompositionAsState(
                    composition = composition,
                    iterations = iterations,
                    isPlaying = showLottie,
                    speed = speed
                )

                // tint (wildcard) — remove or change keyPath if it over-tints
                val dynamicProperties = rememberLottieDynamicProperties(
                    rememberLottieDynamicProperty(
                        property = LottieProperty.COLOR,
                        value = MaterialTheme.colorScheme.primary.toArgb(),
                        keyPath = arrayOf("**")
                    )
                )

                // Fill the box exactly so icon & lottie share the same visual center/size
                LottieAnimation(
                    composition = composition,
                    progress = progress,
                    modifier = Modifier
                        .matchParentSize()
                        .scale(lottieScale)
                        .alpha(lottieAlpha)
                        .semantics { contentDescription = "streak-animation" },
                    dynamicProperties = dynamicProperties
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "$currentStreak",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}



