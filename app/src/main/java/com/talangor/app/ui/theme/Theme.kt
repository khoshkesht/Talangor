package com.talangor.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

private val LightColors = lightColorScheme(
    primary = TalangorPurple,
    onPrimary = TalangorSurface,
    primaryContainer = TalangorPurpleSoft,
    onPrimaryContainer = TalangorPurpleDark,
    secondary = TalangorPurpleDark,
    tertiary = TalangorLilac,
    background = TalangorBackground,
    onBackground = TalangorInk,
    surface = TalangorSurface,
    onSurface = TalangorInk,
    onSurfaceVariant = TalangorTextMuted
)

private val DarkColors = darkColorScheme(
    primary = TalangorPurpleNight,
    onPrimary = TalangorPurpleDark,
    primaryContainer = TalangorPurple,
    onPrimaryContainer = TalangorInkNight,
    secondary = TalangorPurpleSoft,
    tertiary = TalangorPurple,
    background = TalangorPurpleDark,
    onBackground = TalangorInkNight,
    surface = TalangorInk,
    onSurface = TalangorInkNight
)

@Composable
fun TalangorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkColors else LightColors,
            typography = TalangorTypography,
            content = content
        )
    }
}
