package uz.gita.latizx.tbcmobile.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import uz.gita.latizx.tbcmobile.R

/*private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    *//* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    *//*
)*/

@Composable
private fun darkColor(): ColorScheme = darkColorScheme(
    primary = colorResource(R.color.palette_cyan_30),
    secondary = colorResource(R.color.palette_cyan_70),
    tertiary = colorResource(R.color.palette_cyan_10),
    surface = colorResource(R.color.design_dark_default_color_surface),
    background = colorResource(R.color.design_dark_default_color_background),
    onPrimary = colorResource(R.color.design_dark_default_color_background),
    onTertiary = colorResource(R.color.design_dark_default_color_on_background),
)

@Composable
private fun lightColor(): ColorScheme = lightColorScheme(
    primary = colorResource(R.color.palette_cyan_50),
    secondary = colorResource(R.color.palette_cyan_70),
    tertiary = colorResource(R.color.palette_cyan_5),
    onTertiary = colorResource(R.color.design_dark_default_color_background),
    onPrimary = colorResource(R.color.design_dark_default_color_on_background),
    background = colorResource(R.color.design_dark_default_color_on_background),
    surface = colorResource(R.color.design_default_color_surface),
)

@Composable
fun TBCMobileTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColor()
        else -> lightColor()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}