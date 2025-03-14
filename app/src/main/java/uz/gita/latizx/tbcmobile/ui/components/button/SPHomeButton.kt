package uz.gita.latizx.tbcmobile.ui.components.button

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

@Composable
fun SPHomeButton(
    @DrawableRes
    image: Int,
    @StringRes
    text: Int,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(brush = Brush.linearGradient(AppTheme.colorScheme.themeHomeButtonGradient)),
            onClick = {
                onClick()
            },
        ) {
            Icon(
                painter = painterResource(image),
                contentDescription = stringResource(R.string.transaction_transactions),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        Text(
            text = stringResource(text),
            color = AppTheme.colorScheme.textPrimary,
            style = AppTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    SPHomeButton(
        text = R.string.transaction_transactions,
        image = R.drawable.ic_clock_backward_24_regular,
        modifier = Modifier,
    ) {

    }
}