package uz.gita.latizx.tbcmobile.ui.components.items

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import uz.gita.latizx.tbcmobile.ui.components.other.DotBox
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme
import uz.gita.latizx.tbcmobile.utils.toFormatMoney


@Composable
fun ItemCardInfo(
    cards: List<CardsData>,
    @StringRes
    balanceText: Int,
    visibilityData: Boolean = false,
    onClickCard: () -> Unit,
    onClickAddCard: () -> Unit,
) {
    var showBalance by remember { mutableStateOf(visibilityData) }
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors().copy(containerColor = AppTheme.colorScheme.backgroundTertiary),
        shape = RoundedCornerShape(16.dp),
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .clickable(
                    indication = ripple(bounded = true),
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onClickCard()
                },
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
                                AppTheme.colorScheme.backgroundSecondary,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(vertical = 4.dp, horizontal = 12.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.cards_cards),
                            style = AppTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = AppTheme.colorScheme.textPrimary
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = stringResource(balanceText),
                        color = AppTheme.colorScheme.borderAccentGreen,
                        style = AppTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                    )
                    IconButton(
                        onClick = { showBalance = !showBalance }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(
                                if (!showBalance) R.drawable.ic_eye_off
                                else R.drawable.ic_eye
                            ),
                            contentDescription = "ic eye",
                            tint = AppTheme.colorScheme.borderAccentGreen
                        )
                    }
                }
                repeat(cards.size) { index ->
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
                                .padding(horizontal = 8.dp)
                        ) {
                            Image(
                                contentDescription = null,
                                painter = painterResource(if (index % 2 == 0) uz.gita.latizx.comman.R.drawable.ag_ps_humo else uz.gita.latizx.comman.R.drawable.ag_ps_uzpay)
                            )
                        }
                        Column(modifier = Modifier.padding(vertical = 4.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = cards[index].name,
                                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center,
                                color = AppTheme.colorScheme.textPrimary
                            )
                            Box(modifier = Modifier.height(height = 24.dp), contentAlignment = Alignment.Center) {
                                if (showBalance) {
                                    Text(
                                        text = "${cards[index].amount.toString().toFormatMoney()} UZS",
                                        color = colorResource(R.color.palette_green_70),
                                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                        fontWeight = FontWeight.Medium,
                                        textAlign = TextAlign.Center,
                                    )
                                } else {
                                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                        repeat(5) { DotBox(Modifier.size(6.dp)) }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "*${cards[index].pan}",
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            color = AppTheme.colorScheme.textPrimary
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors().copy(containerColor = colorResource(R.color.palette_green_70)),
                    shape = ShapeDefaults.Medium,
                    onClick = { onClickAddCard() }
                ) {
                    Row {
                        Text(
                            text = stringResource(R.string.cards_add),
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = " / ",
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = stringResource(R.string.card_order_benefits_action_text),
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.ic_plus_circle_24_regular),
                        contentDescription = "ic eye",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ItemCardInfo(
        listOf(
            CardsData(
                name = "cardName",
                amount = 100000,
                pan = "1321",
                id = 0,
                expiredMonth = 0,
                expiredYear = 0,
                isVisible = false,
                themeType = 0,
                owner = ""
            )
        ),
        balanceText = R.string.cards_balance_label,
        onClickCard = {},
        onClickAddCard = {}
    )
}