package com.android.movieoftheday.app.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Purple100
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = accent,
    onPrimary = onSecondary,
    secondary = secondary,
    background = background,
    surface = Color.White,
    onSecondary = onSecondary,
    onBackground = onBackground,
    onSurface = Color.Black
)

@Composable
fun MovieOfTheDayTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}