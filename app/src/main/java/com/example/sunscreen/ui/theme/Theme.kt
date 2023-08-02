package com.example.sunscreen.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import com.example.sunscreen.R
import com.example.sunscreen.ui.theme.colors.Background
import com.example.sunscreen.ui.theme.colors.BackgroundDark
import com.example.sunscreen.ui.theme.colors.BackgroundLight
import com.example.sunscreen.ui.theme.colors.Buttons
import com.example.sunscreen.ui.theme.colors.ButtonsDark
import com.example.sunscreen.ui.theme.colors.ButtonsLight
import com.example.sunscreen.ui.theme.colors.Icons
import com.example.sunscreen.ui.theme.colors.IconsDark
import com.example.sunscreen.ui.theme.colors.IconsLight
import com.example.sunscreen.ui.theme.colors.MainBrand
import com.example.sunscreen.ui.theme.colors.MainBrandDark
import com.example.sunscreen.ui.theme.colors.MainBrandLight
import com.example.sunscreen.ui.theme.colors.TextContent
import com.example.sunscreen.ui.theme.colors.TextContentDark
import com.example.sunscreen.ui.theme.colors.TextContentLight
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SunscreenTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val lightColorScheme = lightColorScheme(
        primary = colorResource(id = R.color.color_primary_light),
        secondary = colorResource(id = R.color.color_secondary_light),
        background = colorResource(id = R.color.color_background_light),
        surface = colorResource(id = R.color.color_surface_light),
        error = colorResource(id = R.color.color_error_light),
        onPrimary = colorResource(id = R.color.color_on_primary_light),
        onSecondary = colorResource(id = R.color.color_on_secondary_light),
        onBackground = colorResource(id = R.color.color_on_background_light),
        onSurface = colorResource(id = R.color.color_on_surface_light),
        onError = colorResource(id = R.color.color_on_error_light)
    )

    val darkColorScheme = darkColorScheme(
        primary = colorResource(id = R.color.color_primary_dark),
        secondary = colorResource(id = R.color.color_secondary_dark),
        background = colorResource(id = R.color.color_background_dark),
        surface = colorResource(id = R.color.color_surface_dark),
        error = colorResource(id = R.color.color_error_dark),
        onPrimary = colorResource(id = R.color.color_on_primary_dark),
        onSecondary = colorResource(id = R.color.color_on_secondary_dark),
        onBackground = colorResource(id = R.color.color_on_background_dark),
        onSurface = colorResource(id = R.color.color_on_surface_dark),
        onError = colorResource(id = R.color.color_on_error_dark)
    )

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    val systemUiController = rememberSystemUiController()
    val statusBarColor = Color.Transparent

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = !darkTheme,
        )
    }

    val colors = if (darkTheme) {
        DarkPalette
    } else {
        LightPalette
    }

    CompositionLocalProvider(LocalColors provides colors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


val UiColors: UIColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current

private val LocalColors = staticCompositionLocalOf { LightPalette }

private val LightPalette = UIColors(
    MainBrandLight,
    IconsLight,
    TextContentLight,
    BackgroundLight,
    ButtonsLight
)
private val DarkPalette = UIColors(
    MainBrandDark,
    IconsDark,
    TextContentDark,
    BackgroundDark,
    ButtonsDark
)

data class UIColors(
    val mainBrand: MainBrand,
    val icons: Icons,
    val textContent: TextContent,
    val background: Background,
    val buttons: Buttons
)
