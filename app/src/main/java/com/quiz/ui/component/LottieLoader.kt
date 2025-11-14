package com.quiz.ui.component

import android.R.attr.contentDescription
import androidx.annotation.RawRes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.quiz.R

@Composable
fun LottieLoader(
    size: Dp = 96.dp,
    loop: Boolean = true,
    tintToPrimary: Boolean = false
) {
    val lottieRawRes = R.raw.loader

    Box(
        modifier = Modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // choose spec
        val spec = remember(lottieRawRes) {
            LottieCompositionSpec.RawRes(lottieRawRes)
        }

        val composition by rememberLottieComposition(spec = spec)
        if (composition == null) {
            // show fallback while composition loads
            CircularProgressIndicator(modifier = Modifier.size(size / 2))
            return@Box
        }

        // play forever or once based on loop
        val iterations = if (loop) LottieConstants.IterateForever else 1
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = iterations,
            isPlaying = true
        )

        val dynamicProperties = if (tintToPrimary) {
            rememberLottieDynamicProperties(
                rememberLottieDynamicProperty(
                    property = LottieProperty.COLOR,
                    value = MaterialTheme.colorScheme.primary.toArgb(),
                    keyPath = arrayOf("**")
                )
            )
        } else null

        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .fillMaxSize(),
            dynamicProperties = dynamicProperties
        )
    }
}

@Composable
fun FullScreenLoaderOverlay(
    visible: Boolean,
    loaderSize: Dp = 140.dp
) {
    // smooth fade for scrim alpha
    val targetAlpha = if (visible) 0.6f else 0f
    val scrimAlpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    // Render only when visible to avoid drawing overhead (but still allow alpha animation if you want)
    if (visible || scrimAlpha > 0f) {
        // full screen overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = scrimAlpha))
                // consume pointer input so underlying UI doesn't receive touches
                .pointerInput(visible) {
                    if (visible) {
                        // this will suspend and consume down events while visible
                        awaitPointerEventScope {
                            while (visible) {
                                awaitPointerEvent() // consume events
                            }
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            // Show loader
            LottieLoader(
                size = loaderSize,
                loop = true,
                tintToPrimary = true
            )
        }
    }
}

