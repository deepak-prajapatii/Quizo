package com.quiz.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGreen,
    onPrimary = Color(0xFF0F2914),

    primaryContainer = PrimaryGreenDark,
    onPrimaryContainer = Color.White,

    secondary = SecondaryText,
    onSecondary = Color.White,

    tertiary = PrimaryGreenLight,

    background = Background,
    onBackground = OnBackground,

    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurface,

    outline = Outline,

    error = Error,
    onError = Color.White,
)


private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = Color(0xFF0F2914),

    primaryContainer = PrimaryGreenDark,
    onPrimaryContainer = Color.White,

    secondary = SecondaryText,
    onSecondary = Color.Black,

    tertiary = PrimaryGreenLight,

    // Soft-light mode, not bright white
    background = Color(0xFFF2F4F3),
    onBackground = Color(0xFF1B1C1D),

    surface = Color(0xFFF9FAFA),
    onSurface = Color(0xFF1C1D1E),

    surfaceVariant = Color(0xFFE3E5E6),

    outline = Color(0xFFB8C0C4)
)

@Composable
fun QuizAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
