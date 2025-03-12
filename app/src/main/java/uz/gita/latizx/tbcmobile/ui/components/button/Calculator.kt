package uz.gita.latizx.tbcmobile.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

@Composable
fun CalculatorTab(isCurrency: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(shape = RoundedCornerShape(8.dp), color = AppTheme.colorScheme.backgroundAccentCoolGray)
            .padding(2.5.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .background(
                    color = if (isCurrency) AppTheme.colorScheme.borderInverse else Color.Transparent,
                    shape = if (isCurrency) RoundedCornerShape(8.dp) else RoundedCornerShape(0.dp)
                )
                .clickable(indication = null, interactionSource = null) {
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.rates_exchange_rates_tab),
                style = AppTheme.typography.bodySmall,
                color = if (isCurrency) AppTheme.colorScheme.backgroundAccentCyanTertiary else AppTheme.colorScheme.textPrimary,
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .background(
                    color = if (!isCurrency) AppTheme.colorScheme.borderInverse else Color.Transparent,
                    shape = if (!isCurrency) RoundedCornerShape(8.dp) else RoundedCornerShape(0.dp)
                )
                .clickable(indication = null, interactionSource = null) {
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.rates_exchange_calculator_tab),
                style = AppTheme.typography.bodySmall,
                color = if (!isCurrency) AppTheme.colorScheme.backgroundAccentCyanTertiary else AppTheme.colorScheme.textPrimary,
            )
        }
    }
}