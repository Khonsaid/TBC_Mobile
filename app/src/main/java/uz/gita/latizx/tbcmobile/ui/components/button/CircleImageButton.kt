package uz.gita.latizx.tbcmobile.ui.components.button

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

@Composable
fun CircleImageButton(
    @DrawableRes
    img: Int,
    @StringRes
    text: Int,
    modifier: Modifier = Modifier,
    color: Color = AppTheme.colorScheme.textPrimary,
    onClick: () -> Unit,
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(color = AppTheme.colorScheme.backgroundAccentGray)
                .size(52.dp)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(img),
                contentDescription = null,
                tint = AppTheme.colorScheme.backgroundAccentCoolGrayQuaternary
            )
        }
        Text(
            text = stringResource(text),
            color = color,
            style = AppTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    CircleImageButton(img = R.drawable.ic_plus_24_regular,
        text = R.string.cards_menu_top_up, onClick = {})
}