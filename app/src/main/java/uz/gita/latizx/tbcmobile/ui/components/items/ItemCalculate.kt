package uz.gita.latizx.tbcmobile.ui.components.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme
import uz.gita.latizx.tbcmobile.utils.toFormatMoney

@Composable
fun ItemCalculate(
    fromUZS: Boolean,
    rate: String,
    value: String = "0.00",
    readOnly: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(shape = RoundedCornerShape(8.dp), color = AppTheme.colorScheme.backgroundAccentCoolGray)
            .padding(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(if (fromUZS) R.drawable.ag_flag_uz else R.drawable.ag_flag_us), contentDescription = null,
                modifier = Modifier
                    .size(width = 44.dp, height = 36.dp)
                    .clip(shape = RoundedCornerShape(6.dp)),
            )
            Text(
                text = if (fromUZS) "UZS" else "USD",
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                color = AppTheme.colorScheme.textPrimary
            )
            Spacer(modifier = Modifier.weight(1f))
            TextField(
                placeholder = {
                    Text(
                        "0",
                        style = AppTheme.typography.bodySmall,
                        textAlign = TextAlign.Start
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                readOnly = readOnly,
                modifier = Modifier
                    .width(200.dp)
                    .height(52.dp),
                value = value,
                onValueChange = { newValue ->
//                    if (newValue.isEmpty() || newValue.matches(Regex("\\d+"))) {
                    if (newValue.length <= 8 || (newValue.length > 8 && value.length > 8)) {
                        onValueChange(newValue)
                    }
//                    }
                },
                textStyle = AppTheme.typography.bodySmall.copy(color = AppTheme.colorScheme.textPrimary),
                suffix = {
                    Text(
                        text = if (fromUZS) "UZS" else "$",
                        style = AppTheme.typography.bodyMedium.copy(color = AppTheme.colorScheme.textPrimary),
                        textAlign = TextAlign.Center
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = AppTheme.colorScheme.backgroundPrimary,
                    unfocusedContainerColor = AppTheme.colorScheme.backgroundPrimary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
        }
        Row {
            Text(
                text = if (fromUZS) "1000 UZS = " else "1 USD = ",
                color = AppTheme.colorScheme.textPrimary,
                fontSize = AppTheme.typography.bodySmall.fontSize,
                textAlign = TextAlign.Start
            )
            Text(
                text = rate.toFormatMoney() + if (fromUZS) " UZS" else " USD",
                color = AppTheme.colorScheme.textPrimary,
                fontSize = AppTheme.typography.bodySmall.fontSize,
                textAlign = TextAlign.Start
            )
        }
    }
}