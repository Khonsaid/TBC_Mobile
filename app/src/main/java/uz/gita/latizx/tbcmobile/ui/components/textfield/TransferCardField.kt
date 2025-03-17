package uz.gita.latizx.tbcmobile.ui.components.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.latizx.comman.formatWithSeparator
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme
import uz.gita.latizx.tbcmobile.utils.toFormatCard

@Composable
fun TransferCardField(
    img: Int = 0,
    name: String,
    cardData: String,
    modifier: Modifier = Modifier,
    hasArrowDown: Boolean = false,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .height(56.dp)
//                .border(
//                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
//                )
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(12.dp))
                .background(color = colorResource(R.color.palette_gray_5))
                .clickable { onClick() }
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.padding(vertical = 4.dp),
            ) {
                Text(
                    text = if (hasArrowDown) name else name.uppercase(),
                    style = AppTheme.typography.bodySmall
                )
                Text(
                    text = if (hasArrowDown) cardData.formatWithSeparator() + " UZS" else cardData.toFormatCard(),
                    style = AppTheme.typography.bodySmall,
                    color = if (hasArrowDown) colorResource(R.color.palette_green_70) else AppTheme.colorScheme.textPrimary
                )
            }
            if (hasArrowDown) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TransferCardField(name = "Vali", cardData = "12312")
}