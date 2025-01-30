package uz.gita.latizx.tbcmobile.screen.main.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import uz.gita.latizx.tbcmobile.R

class PaymentsScreen : Screen {
    @Composable
    override fun Content() {

        PaymentsScreenContent()
    }
}

@Composable
private fun PaymentsScreenContent(

) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.palette_cyan_60))
                    .fillMaxHeight(0.25f)
            ) {
                Row(horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = stringResource(R.string.payments_title),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp),
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_close_circle_regular),
                            contentDescription = "close btn"
                        )
                    }
                }
            }
            SpPaymentTemplateAddItem()
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PaymentsScreenContent()
}

@Composable
private fun SpPaymentTemplateAddItem() {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFFF9EDFF), Color(0xFFD5FAFF)),
        start = Offset(-1.538f, 48f),
        end = Offset(60.418f, 28.847f)
    )
    Row(
        modifier = Modifier
            .padding(vertical = 12.dp)
            .padding(horizontal = 8.dp),
    ) {
        Card(
            modifier = Modifier.size(48.dp),
            elevation = CardDefaults.cardElevation(1.dp),
        ) {
            Box(
                modifier = Modifier.background(brush = gradientBrush, shape = RoundedCornerShape(10.dp))
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                        .padding(8.dp),
                    painter = painterResource(R.drawable.ic_plus_24_regular),
                    contentDescription = "ic_plus_24_regular",
                    tint = colorResource(R.color.palette_cyan_50)
                )
            }
        }

        Column {
            Text(
                text = stringResource(R.string.payment_create_main),
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp),
                color = colorResource(R.color.palette_cyan_50)
            )

            Text(
                text = stringResource(R.string.payment_create_main_subtitle),
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                modifier = Modifier.padding(horizontal = 12.dp),
                color = colorResource(R.color.secondary_text_default_material_dark)
            )
        }
    }
}