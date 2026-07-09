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
    primary = TalangorBlue,
    secondary = TalangorGreen,
    tertiary = TalangorRose,
    background = TalangorBackground
)

private val DarkColors = darkColorScheme(
    primary = TalangorBlueDark,
    secondary = TalangorGreenDark,
    tertiary = TalangorRoseDark
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
