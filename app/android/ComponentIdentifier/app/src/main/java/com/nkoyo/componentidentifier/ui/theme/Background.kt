package com.nkoyo.componentidentifier.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class NkBackgroundTheme(
    val surface: Color = Color.Unspecified,
    val onSurface: Color = Color.Unspecified,
    val surfaceVariant: Color = Color.Unspecified,
    val onSurfaceVariant: Color = Color.Unspecified,
    val outline: Color = Color.Unspecified,
    val primary: Color = Color.Unspecified,
    val onPrimary: Color = Color.Unspecified,
)

val LocalBackgroundTheme = staticCompositionLocalOf { NkBackgroundTheme() }