package uz.gita.latizx.tbcmobile.screen.main.home.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.latizx.comman.model.CardsData
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme


@Composable
fun ItemCurrency(
    onClickCard: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .clickable {
                onClickCard()
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors().copy(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            colorResource(R.color.palette_gray_5),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 4.dp, horizontal = 12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.my_space_rates_title),
                        style = AppTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }

            }
            repeat(1) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp),
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
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "1 ${stringResource(R.string.deposit_chooser_usd)}",
                            color = colorResource(R.color.palette_green_70),
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(52.dp),
            colors = ButtonDefaults.buttonColors().copy(containerColor = colorResource(R.color.palette_green_70)),
            shape = ShapeDefaults.Medium,
            onClick = { onClickCard() }
        ) {
                Text(
                    text = stringResource(R.string.rates_my_space_button_title),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.ic_chevron_right_24_regular),
                contentDescription = "ic eye",
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ItemCurrency(

    ){}
}