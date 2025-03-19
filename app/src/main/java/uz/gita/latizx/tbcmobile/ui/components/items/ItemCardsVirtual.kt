package uz.gita.latizx.tbcmobile.ui.components.items

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme
import uz.gita.latizx.comman.R as common

@Composable
fun ItemCardsVirtual(
    @StringRes
    title: Int,
    @StringRes
    text: Int,
    @DrawableRes
    image: Int,
    @DrawableRes
    imgCard: Int,
    cardColor: Int,
    onClick: () -> Unit,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(cardColor)
        )
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .height(40.dp)
                    .width(100.dp)
                    .rotate(45f),
                painter = painterResource(image),
                contentDescription = ""
            )
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .padding(top = 12.dp)
                    .fillMaxWidth(0.7f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .background(colorResource(R.color.palette_gray_5), shape = RoundedCornerShape(8.dp))
                            .height(32.dp)
                            .width(44.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            contentDescription = null,
                            painter = painterResource(imgCard)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color.White.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .height(32.dp)
                            .width(44.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "UZS",
                            color = AppTheme.colorScheme.textOnPrimary,
                            style = AppTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400)
                        )
                    }
                }
                Text(
                    text = stringResource(title),
                    style = AppTheme.typography.titleSmall,
                    color = AppTheme.colorScheme.textOnPrimary,
                )
                Text(
                    text = stringResource(text),
                    color = AppTheme.colorScheme.textOnPrimary,
                    style = AppTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ItemCardsVirtual(
        title = R.string.intro_title_page_1,
        text = R.string.cards_catalog_add_card,
        image = common.drawable.ill_credit_card_lg,
        cardColor = common.color.pfm_gradient_end_color,
        imgCard = uz.gita.latizx.comman.R.drawable.ag_ps_humo
    ) {

    }
}