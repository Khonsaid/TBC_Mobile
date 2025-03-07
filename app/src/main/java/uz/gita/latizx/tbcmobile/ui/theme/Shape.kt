package uz.gita.latizx.tbcmobile.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Immutable
class AppShape(
    val medium: Shape,
    val large: Shape,
    val small: Shape,
    val smallest: Shape,
)

val appDefaultShape = AppShape(
    smallest = RoundedCornerShape(10.dp),
    medium = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
    small = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
    large = androidx.compose.foundation.shape.RoundedCornerShape(30.dp),
)


internal val LocalShape = staticCompositionLocalOf { appDefaultShape }