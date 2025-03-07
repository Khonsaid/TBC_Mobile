package uz.gita.latizx.tbcmobile.ui.components.button

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R

@Composable
fun CircleImageButton(
    @DrawableRes
    img: Int,
    @StringRes
    text: Int,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = MaterialTheme.typography.bodySmall.fontSize,
    color: Color = MaterialTheme.colorScheme.onTertiary,
    backgroundColor: Int = R.color.palette_gray_5,
    fontWeight: FontWeight? = FontWeight.Normal,
    onClick: () -> Unit,
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .background(color = colorResource(backgroundColor))
                .size(52.dp),
            onClick = { onClick() }
        ) {
            Icon(
                painter = painterResource(img),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Text(
            text = stringResource(text),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
    }
}

@Preview
@Composable
private fun Preview() {
    CircleImageButton(img = R.drawable.ic_plus_24_regular,
        text = R.string.cards_menu_top_up, onClick = {})
}