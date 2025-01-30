package uz.gita.latizx.tbcmobile.screen.components.other

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R


@Composable
fun DotBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(12.dp)
            .background(
                color = colorResource(R.color.home_btn_balance_title_white_theme_color),
                shape = CircleShape
            )
    )
}