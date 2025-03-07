package uz.gita.latizx.tbcmobile.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

object AppTheme {
    val colorScheme: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val shape: AppShape
        @Composable
        @ReadOnlyComposable
        get() = LocalShape.current

    val isDark: Boolean
        @Composable
        @ReadOnlyComposable
        get() = isSystemInDarkTheme()
}

@Composable
fun TBCMobileTheme(
    shape: AppShape = AppTheme.shape,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        isDarkTheme -> darkColor
        else -> lightColor
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = if (!isDarkTheme) Color(0xFF0A9AA4).toArgb() else Color(0xFF131511).toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = false
        }
    }

    CompositionLocalProvider(
        LocalColorScheme provides colorScheme,
        LocalTypography provides typography,
        LocalShape provides shape,
    ) {
        ProvideTextStyle(value = typography.bodyMedium, content = content)
    }
}