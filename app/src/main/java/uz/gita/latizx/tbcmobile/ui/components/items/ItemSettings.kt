package uz.gita.latizx.tbcmobile.ui.components.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme


@Composable
fun ItemSettings(img: Int, text: Int, onClick: () -> Unit) {
    Row(Modifier
        .clickable(
            indication = ripple(), interactionSource = remember { MutableInteractionSource() }
        ) {
            onClick()
        }
        .padding(12.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(img), contentDescription = null, tint = AppTheme.colorScheme.iconAccentCyan, modifier = Modifier.size(32.dp))
        Spacer(Modifier.width(24.dp))
        Text(
            text = stringResource(text), style = AppTheme.typography.bodyMedium, color = AppTheme.colorScheme.textPrimary
        )
        Spacer(Modifier.weight(1f))
        Icon(modifier = Modifier.size(24.dp), painter = painterResource(R.drawable.ic_chevron_right_24_regular), contentDescription = null, tint = AppTheme.colorScheme.borderSecondary)
    }
}
