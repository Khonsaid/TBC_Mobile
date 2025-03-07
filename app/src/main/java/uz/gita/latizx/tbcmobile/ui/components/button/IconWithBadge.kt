package uz.gita.latizx.tbcmobile.ui.components.button

import android.graphics.Color.parseColor
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

@Composable
fun IconWithNotificationBadge(
    @DrawableRes iconRes: Int,
    badgeContent: String?,
    badgeColor: Color = Color(parseColor("#ff3b30")),
) {
    ElevatedCard(
        modifier = Modifier
            .size(40.dp)
            .clip(ShapeDefaults.Small),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colorScheme.themeBottomSheetHeaderIconColor,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.5.dp
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(ShapeDefaults.Small)
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
            )

            if (badgeContent == null) {
                Spacer(
                    modifier = Modifier
                        .size(12.dp)
                        .background(color = badgeColor, shape = CircleShape)
                        .align(Alignment.TopEnd)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    IconWithNotificationBadge(
        iconRes = R.drawable.ic_home_user,
        "1"
    )
}