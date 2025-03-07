package uz.gita.latizx.tbcmobile.ui.components.items

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.comman.R as common

@Composable
fun ItemHomeVertical(
    @StringRes
    title: Int,
    @StringRes
    text: Int,
    @DrawableRes
    image: Int,
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
                    .fillMaxWidth(0.4f),
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
                Text(
                    text = stringResource(title).uppercase(),
                    fontSize = MaterialTheme.typography.displaySmall.fontSize,
                    color = colorResource(R.color.palette_cyan_70),
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = stringResource(text),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.ExtraBold
                )
                IconButton(
                    onClick = onClick
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_chevron_right_24_regular),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ItemHomeVertical(
        title = R.string.my_space_card_title,
        text = R.string.cards_catalog_add_card,
        image = common.drawable.ill_credit_card_lg,
        cardColor = common.color.pfm_gradient_end_color
    ) {

    }
}