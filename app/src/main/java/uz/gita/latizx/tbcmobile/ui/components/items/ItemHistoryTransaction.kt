package uz.gita.latizx.tbcmobile.ui.components.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.components.image.IconForItems
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

@Composable
fun ItemHistoryTransaction(
    name: String,
    date: String,
    transferSum: String,
    onTap: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = ripple(bounded = true),
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onTap.invoke()
            },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconForItems(icon = R.drawable.ic_card_transfer_24_regular)

        val nameWeight = remember(transferSum) {
            if (transferSum.length > 10) 0.4f else 0.6f
        }
        val sumWeight = 1f - nameWeight // TransferSum uchun joyni qoldiramiz

        Column(
            modifier = Modifier.weight(nameWeight),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    color = AppTheme.colorScheme.textPrimary,
                    fontWeight = FontWeight.W400,
                    fontSize = AppTheme.typography.bodySmall.fontSize,
                )
            )
            Text(
                text = date,
                fontSize = AppTheme.typography.bodySmall.fontSize,
                color = AppTheme.colorScheme.textPrimary,
            )
        }
        Column(
            modifier = Modifier
                .weight(sumWeight)
                .padding(end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$transferSum UZS",
                fontWeight = FontWeight.W600,
                fontSize = AppTheme.typography.bodySmall.fontSize,
                color = AppTheme.colorScheme.textPrimary,
            )
            Text(
                text = stringResource(R.string.transaction_completed_status),
                style = TextStyle(
                    color = AppTheme.colorScheme.textPrimary,
                    fontSize = AppTheme.typography.bodySmall.fontSize,
                )
            )
        }
    }
}

@Preview()
@Composable
private fun Preview() {
    Surface(modifier = Modifier.fillMaxWidth()) {
        ItemHistoryTransaction("Alisher", "22:02", "10000"){}
    }
}