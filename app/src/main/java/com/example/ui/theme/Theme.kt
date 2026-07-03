package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = SchoolPrimary,
    secondary = SchoolSecondary,
    tertiary = SchoolTertiary,
    background = SchoolBackgroundDark,
    surface = SchoolSurfaceDark,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color(0xFFECEFF1),
    onSurface = Color(0xFFECEFF1),
    surfaceVariant = Color(0xFF263238),
    onSurfaceVariant = Color(0xFFCFD8DC)
)

private val LightColorScheme = lightColorScheme(
    primary = SchoolPrimary,
    secondary = SchoolSecondary,
    tertiary = SchoolTertiary,
    background = SchoolBackgroundLight,
    surface = SchoolSurfaceLight,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color(0xFF1C2833),
    onSurface = Color(0xFF1C2833),
    surfaceVariant = Color(0xFFE5E7E9),
    onSurfaceVariant = Color(0xFF566573)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
