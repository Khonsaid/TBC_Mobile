package uz.gita.latizx.tbcmobile.ui.components.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme


@Composable
fun IconForItems(
    modifierIcon: Modifier = Modifier,
    sizeBox: Dp = 48.dp,
    @DrawableRes
    icon: Int,
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .border(
                border = BorderStroke(
                    width = 2.dp,
                    color = AppTheme.colorScheme.backgroundAccentCoolGray.copy(alpha = 40f)
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .size(sizeBox),
    ) {
        Icon(
            modifier = modifierIcon
                .padding(8.dp)
                .align(Alignment.Center)
                .fillMaxSize(),
            painter = painterResource(icon),
            contentDescription = null,
            tint = AppTheme.colorScheme.iconAccentCyan
        )
    }
}

@Preview
@Composable
private fun Preview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        IconForItems(icon = R.drawable.ic_card_transfer_24_regular)
    }
}