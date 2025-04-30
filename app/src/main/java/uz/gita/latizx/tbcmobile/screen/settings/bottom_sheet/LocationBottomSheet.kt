package uz.gita.latizx.tbcmobile.screen.settings.bottom_sheet

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.parcelize.IgnoredOnParcel
import uz.gita.latizx.comman.model.map.LocationData
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

data class LocationBottomSheet(
    val location: LocationData,
) : Screen {

    @IgnoredOnParcel
    var onSelectCard: (Int) -> Unit = {}

    @IgnoredOnParcel
    var onDismissRequest: () -> Unit = {}

    @Composable
    override fun Content() {
        ChooseCardBottomSheetContent(
            location = location,
            onSelectCard = onSelectCard,
            onDismissRequest = onDismissRequest
        )
    }
}

@Composable
private fun ChooseCardBottomSheetContent(
    location: LocationData,
    onSelectCard: (Int) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(AppTheme.colorScheme.backgroundTertiary)
            .padding(16.dp),
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Icon(painter = painterResource(R.drawable.ic_map_pin_24_regular), contentDescription = null, tint = Color(0xFF00a2b7))
                Text(
                    text = location.attributes.title,
                    style = AppTheme.typography.bodyLarge,
                    color = AppTheme.colorScheme.textPrimary
                )
            }
            Text(
                text = location.attributes.address,
                style = AppTheme.typography.bodySmall,
                color = AppTheme.colorScheme.textPrimary
            )
            Row {
                Text(
                    text = "Ish vaqti: ",
                    style = AppTheme.typography.bodySmall,
                    color = AppTheme.colorScheme.textPrimary
                )
                Text(
                    text = location.attributes.description,
                    style = AppTheme.typography.bodySmall,
                    color = AppTheme.colorScheme.textPrimary
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
//    ChooseCardBottomSheetContent(
//
//        onSelectCard = {
//        },
//        onDismissRequest = {},
//    )
}