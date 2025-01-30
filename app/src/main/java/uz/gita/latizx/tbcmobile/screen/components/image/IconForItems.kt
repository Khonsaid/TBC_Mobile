package uz.gita.latizx.tbcmobile.screen.components.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R


@Composable
fun IconForItems(
    modifierBox: Modifier = Modifier,
    modifierIcon: Modifier = Modifier,
    @DrawableRes
    icon: Int,
) {
    Box(
        modifier = modifierBox
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = colorResource(R.color.palette_gray_10)
                )
            )
            .size(48.dp),
    ) {
        Icon(
            modifier = modifierIcon
                .align(Alignment.Center)
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(12.dp)),
            painter = painterResource(icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
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