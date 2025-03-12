package uz.gita.latizx.tbcmobile.ui.components.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import uz.gita.latizx.comman.model.ExchangeRateModel
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme
import uz.gita.latizx.tbcmobile.utils.getCountryCodeByCurrency
import uz.gita.latizx.tbcmobile.utils.toFormatMoney

@Composable
fun CurrencyTBC(exchangeRateModel: ExchangeRateModel?) {
    val usdRate = exchangeRateModel?.rate?.toDouble()?.toInt() ?: 0
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(5f),
                text = stringResource(R.string.rates_exchange_rate),
                style = AppTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                color = AppTheme.colorScheme.textPrimary
            )
            Text(
                modifier = Modifier.weight(2.5f),
                text = stringResource(R.string.rates_exchange_buy),
                style = AppTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                color = AppTheme.colorScheme.textPrimary
            )
            Text(
                modifier = Modifier.weight(2.5f),
                text = stringResource(R.string.rates_exchange_sell),
                style = AppTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                color = AppTheme.colorScheme.textPrimary
            )
        }
        Row {
            Row(
                modifier = Modifier.weight(5f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(colorResource(R.color.palette_gray_5), shape = AppTheme.shape.small)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ag_flag_us),
                        contentDescription = null
                    )
                }
                Column(modifier = Modifier.padding(vertical = 4.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "USD",
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start,
                        color = AppTheme.colorScheme.textPrimary
                    )

                    Text(
                        text = "1 ${stringResource(R.string.deposit_chooser_usd)}",
                        color = AppTheme.colorScheme.textPrimary,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .weight(2.5f), verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = (usdRate - ((usdRate / 100) * 0.2)).toString().toFormatMoney(hasTail = true),
                    color = AppTheme.colorScheme.textPrimary,
                    fontSize = AppTheme.typography.bodySmall.fontSize,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "USD",
                    fontSize = AppTheme.typography.bodySmall.fontSize,
                    textAlign = TextAlign.Start,
                    color = AppTheme.colorScheme.textPrimary
                )
            }
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .weight(2.5f), verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = (usdRate + ((usdRate / 100) * 0.4)).toString().toFormatMoney(hasTail = true),
                    color = AppTheme.colorScheme.textPrimary,
                    fontSize = AppTheme.typography.bodySmall.fontSize,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "USD",
                    fontSize = AppTheme.typography.bodySmall.fontSize,
                    textAlign = TextAlign.Start,
                    color = AppTheme.colorScheme.textPrimary
                )
            }
        }
    }
}

@Composable
fun ItemCurrCurrency(exchangeRateModel: ExchangeRateModel?) {
    val currLang = LocalContext.current.resources.configuration.locales[0].language
    val flag = if (exchangeRateModel?.ccy == "EUR")
        "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Flag_of_Europe.svg/2560px-Flag_of_Europe.svg.png"
    else exchangeRateModel?.ccy;

    Row {
        Row(
            modifier = Modifier.weight(5f),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(colorResource(R.color.palette_gray_5), shape = AppTheme.shape.small)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(if (exchangeRateModel?.ccy == "EUR") flag else "https://flagpedia.net/data/flags/h80/${flag?.getCountryCodeByCurrency()}.png")
                        .crossfade(true)
                        .build(),
                    placeholder = null,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(width = 44.dp, height = 32.dp).clip(shape = RoundedCornerShape(3.dp)),
                )
            }
            Column(modifier = Modifier.padding(vertical = 4.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = exchangeRateModel?.ccy.toString(),
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start,
                    color = AppTheme.colorScheme.textPrimary
                )

                Text(
                    text = "1 ${if (currLang == "ru") exchangeRateModel?.ccyNmRU else exchangeRateModel?.ccyNmUZ}",
                    color = AppTheme.colorScheme.textPrimary,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start
                )
            }
        }
        Text(
            text = exchangeRateModel?.rate?.toFormatMoney().toString() + " UZS",
            color = AppTheme.colorScheme.textPrimary,
            fontSize = AppTheme.typography.bodySmall.fontSize,
            textAlign = TextAlign.Start
        )
    }
}

