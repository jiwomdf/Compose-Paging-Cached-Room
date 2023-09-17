package com.katilijiwoadiwiyono.imagegallerycompose.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    onBackground = md_theme_light_background,
    onSecondary = md_theme_light_primary,
    onTertiary = md_theme_light_primary,
    onSurface = md_theme_light_primary,
    surface = md_theme_light_primary,
    tertiary = md_theme_light_ternary,
    tertiaryContainer = md_theme_light_ternaryContainer
)

private val LightColorScheme = lightColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    onBackground = md_theme_dark_background,
    onSecondary = md_theme_dark_primary,
    onTertiary = md_theme_dark_primary,
    onSurface = md_theme_dark_primary,
    surface = md_theme_dark_primary,
    tertiary = md_theme_light_ternary,
    tertiaryContainer = md_theme_light_ternaryContainer
)

@Composable
fun ImageGalleryComposeTheme(
    isUseDarkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isUseDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        isUseDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val statusBarColor = if(isUseDarkTheme) md_theme_dark_background else md_theme_light_background
            window.statusBarColor = statusBarColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isUseDarkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}