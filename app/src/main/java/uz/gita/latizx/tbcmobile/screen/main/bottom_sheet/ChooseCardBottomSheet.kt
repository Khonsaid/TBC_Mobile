package uz.gita.latizx.tbcmobile.screen.main.bottom_sheet

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.parcelize.IgnoredOnParcel
import uz.gita.latizx.comman.model.CardsData
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme
import uz.gita.latizx.tbcmobile.utils.toFormatMoney


data class ChooseCardBottomSheet(
    val cardList: List<CardsData>,
) : Screen {

    @IgnoredOnParcel
    var onSelectCard: (Int) -> Unit = {}

    @IgnoredOnParcel
    var onDismissRequest: () -> Unit = {}

    @Composable
    override fun Content() {
        ChooseCardBottomSheetContent(
            cards = cardList,
            onSelectCard = onSelectCard,
            onDismissRequest = onDismissRequest
        )
    }
}

@Composable
private fun ChooseCardBottomSheetContent(
    cards: List<CardsData>,
    onSelectCard: (Int) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(AppTheme.colorScheme.backgroundTertiary)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Color.Gray)
                .padding(bottom = 16.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
        ) {
            repeat(cards.size) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSelectCard(index)
                            onDismissRequest()
                        }
                        .padding(vertical = 12.dp, horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(colorResource(R.color.palette_gray_5), shape = AppTheme.shape.small)
                            .padding(horizontal = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(if (index % 2 == 0) uz.gita.latizx.comman.R.drawable.ag_ps_uzpay else uz.gita.latizx.comman.R.drawable.ag_ps_humo),
                            contentDescription = null
                        )
                    }
                    Column(modifier = Modifier.padding(vertical = 4.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = cards[index].name,
                            style = AppTheme.typography.bodySmall,
                            color = AppTheme.colorScheme.textPrimary,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "${cards[index].amount.toString().toFormatMoney()} UZS",
                            color = colorResource(R.color.palette_green_70),
                            style = AppTheme.typography.bodySmall,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "*${cards[index].pan}",
                        style = AppTheme.typography.bodySmall,
                        color = AppTheme.colorScheme.textPrimary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ChooseCardBottomSheetContent(
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
        onSelectCard = {
        },
        onDismissRequest = {},
    )
}