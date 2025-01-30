package uz.gita.latizx.tbcmobile.screen.components.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R

@Composable
fun AppTopBar(
    text: Int,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(56.dp),
    onClickBack: () -> Unit,
    hasPrevBtn: Boolean = false,
    onClickPrev: () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    Column(modifier = Modifier.background(colorResource(R.color.palette_gray_20))) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (hasPrevBtn) {
                    IconButton(onClick = onClickPrev) {
                        Icon(
                            painter = painterResource(R.drawable.ic_chevron_left_24_regular),
                            contentDescription = "close btn"
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(16.dp))
                }
                Text(
                    text = stringResource(text),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = MaterialTheme.colorScheme.onTertiary,
                    fontFamily = FontFamily(Font(R.font.roboto_bold))
                )
            }
            IconButton(
                modifier = Modifier.padding(end = 12.dp),
                onClick = onClickBack
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_close_circle_regular),
                    contentDescription = "close btn"
                )
            }
        }
        content()
    }
}

@Preview
@Composable
private fun Preview() {
    AppTopBar(0, onClickBack = {}, onClickPrev = {})
}