package uz.gita.latizx.tbcmobile.screen.main.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

@Composable
fun ItemSupport(
    onClickCard: () -> Unit,
) {
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
            Modifier
                .clip(RoundedCornerShape(12.dp))
                .clickable(indication = ripple(true), interactionSource = remember { MutableInteractionSource() }) {
                    onClickCard()
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
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
                        text = stringResource(R.string.home_support_title),
                        style = AppTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = AppTheme.colorScheme.textPrimary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ItemSupport(
        onClickCard = {},
    )
}