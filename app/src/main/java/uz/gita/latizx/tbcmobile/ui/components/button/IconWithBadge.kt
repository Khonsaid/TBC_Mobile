package uz.gita.latizx.tbcmobile.ui.components.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

@Composable
fun IconWithNotificationBadge(
    @DrawableRes iconRes: Int,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .size(40.dp),
        shape = ShapeDefaults.Medium,
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colorScheme.themeBottomSheetHeaderIconColor,
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colorScheme.themeBottomSheetHeaderIconColor)
                .clip(ShapeDefaults.Small)
                .clickable{
                    onClick()
                },
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    IconWithNotificationBadge(
        iconRes = R.drawable.ic_home_user,
    ){}
}