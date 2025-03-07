package uz.gita.latizx.tbcmobile.ui.components.other

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme


@Composable
fun DotBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(12.dp)
            .background(
                color = AppTheme.colorScheme.backgroundAccentCoolGrayTertiary,
                shape = CircleShape
            )
    )
}