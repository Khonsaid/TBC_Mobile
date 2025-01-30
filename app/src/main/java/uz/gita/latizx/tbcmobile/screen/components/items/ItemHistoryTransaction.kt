package uz.gita.latizx.tbcmobile.screen.components.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.latizx.comman.formatWithSeparator
import uz.gita.latizx.tbcmobile.R

import uz.gita.latizx.tbcmobile.screen.components.image.IconForItems

@Composable
fun ItemHistoryTransaction(
    name: String,
    date: String,
    transferSum: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconForItems(icon = R.drawable.ic_card_transfer_24_regular)
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = name,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onTertiary,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                )
            )
            Text(
                text = date,
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                color = MaterialTheme.colorScheme.onTertiary,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .padding(end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = transferSum.formatWithSeparator() + " UZS",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                color = MaterialTheme.colorScheme.onTertiary,
            )
            Text(
                text = stringResource(R.string.transaction_completed_status),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onTertiary,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                )
            )
        }
    }
}

@Preview()
@Composable
private fun Preview() {
    Surface(modifier = Modifier.fillMaxWidth()) {
        ItemHistoryTransaction("Alisher", "22:02", "10000")
    }
}