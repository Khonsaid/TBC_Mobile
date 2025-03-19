package uz.gita.latizx.tbcmobile.screen.card.dialog

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme
import kotlin.math.absoluteValue

@Parcelize
data class DateSelectDialog(
    val list: List<String>,
) : Screen, Parcelable {

    @IgnoredOnParcel
    var onDismissRequest: (String) -> Unit = {}

    @Composable
    override fun Content() {
        DateSelectBottomSheet(
            list = list,
            onDismissRequest = onDismissRequest
        )
    }
}

@Composable
private fun DateSelectBottomSheet(
    list: List<String>,
    onDismissRequest: (String) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { list.size })
    var currentIndex = pagerState.currentPage

    Column(
        modifier = Modifier.fillMaxWidth().background(color = AppTheme.colorScheme.backgroundTertiary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(AppTheme.colorScheme.backgroundBrandTertiary)
                .clickable { onDismissRequest(list[currentIndex]) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.components_confirm),
                color = AppTheme.colorScheme.textPrimary,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            )
        }

        VerticalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentPadding = PaddingValues(
                vertical = 50.dp
            ),
        ) { page ->
            Text(
                text = list[page],
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                        alpha = lerp(
                            start = 0.3f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        scaleX = lerp(
                            start = 0.8f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        scaleY = scaleX
                    },
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Medium,
                color = AppTheme.colorScheme.textPrimary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DateSelectBottomSheet(listOf("1", "2", "3", "4", "5")) {

    }
}



