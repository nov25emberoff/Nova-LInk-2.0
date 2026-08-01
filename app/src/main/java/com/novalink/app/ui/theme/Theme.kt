package com.novalink.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val NovaColorScheme = darkColorScheme(
    primary = NovaOrange,
    onPrimary = NovaBlack,
    secondary = NovaOrangeLight,
    onSecondary = NovaBlack,
    background = NovaBlack,
    onBackground = NovaOnSurface,
    surface = NovaSurface,
    onSurface = NovaOnSurface,
    surfaceVariant = NovaSurfaceVariant,
    onSurfaceVariant = NovaOnSurfaceMuted,
    error = NovaOrangeDark
)

@Composable
fun NovaLinkTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = NovaColorScheme,
        typography = NovaTypography,
        content = content
    )
}
