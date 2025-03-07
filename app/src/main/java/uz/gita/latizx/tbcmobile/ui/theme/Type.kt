package uz.gita.latizx.tbcmobile.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import uz.gita.latizx.tbcmobile.R

val fontInter = FontFamily(
    Font(
        resId = R.font.roboto_medium,
        weight = FontWeight.Medium,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.roboto_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.roboto_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    ),
)

val typography = Typography(
    titleLarge = TextStyle(
        fontFamily = fontInter,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = fontInter,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = fontInter,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = fontInter,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = fontInter,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = fontInter,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    ),
    captionLarge = TextStyle(
        fontFamily = fontInter,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
    ),
    captionMedium = TextStyle(
        fontFamily = fontInter,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
    ),
    captionSmall = TextStyle(
        fontFamily = fontInter,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
    ),
)

@Immutable
class Typography(
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,
    val captionLarge: TextStyle,
    val captionMedium: TextStyle,
    val captionSmall: TextStyle,
)

internal val LocalTypography = staticCompositionLocalOf { typography }