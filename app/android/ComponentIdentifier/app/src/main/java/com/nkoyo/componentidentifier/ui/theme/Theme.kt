package com.nkoyo.componentidentifier.ui.theme

import android.app.Activity
import android.os.Build
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@VisibleForTesting
val LightColorScheme = lightColorScheme(
    primary = Purple95,
    onPrimary = Color.Black,
    primaryContainer = Purple90,
    onPrimaryContainer = Color.Black,
    secondary = Orange40,
    onSecondary = Color.White,
    secondaryContainer = Orange40,
    onSecondaryContainer = Color.White,
    tertiary = GreenGray30,
    onTertiary = Color.White,
    tertiaryContainer = GreenGray50,
    onTertiaryContainer = Color.White,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    surfaceVariant = Purple95,
    onSurfaceVariant = Color.Black,
    inverseSurface = Purple30,
    inverseOnSurface = Color.White,
    outline = Black,
)

val DarkColorScheme = darkColorScheme(
    primary = Purple95,
    onPrimary = Color.Black,
    primaryContainer = Purple90,
    onPrimaryContainer = Color.Black,
    secondary = Orange40,
    onSecondary = Color.White,
    secondaryContainer = Orange40,
    onSecondaryContainer = Color.White,
    tertiary = GreenGray30,
    onTertiary = Color.White,
    tertiaryContainer = GreenGray50,
    onTertiaryContainer = Color.White,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = DarkGreenGray10,
    onBackground = Color.White,
    surface = DarkGreenGray10,
    onSurface = Color.White,
    surfaceVariant = Purple95,
    onSurfaceVariant = Color.Black,
    inverseSurface = Purple30,
    inverseOnSurface = Color.White,
    outline = White,
)

@Composable
fun ComponentIdentifierTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.outline.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    val systemUiController = rememberSystemUiController()
    val outlineColor = MaterialTheme.colorScheme.outline
    SideEffect {
        systemUiController.apply {
            setStatusBarColor(color = outlineColor)
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = NkTypography,
        content = content
    )
}