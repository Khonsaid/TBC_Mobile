package uz.gita.latizx.tbcmobile.screen.main.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.components.topbar.AppTopBar
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

class SettingsScreen : Screen {
    @Composable
    override fun Content() {
        SettingsScreenContent()
    }
}

@Composable
private fun SettingsScreenContent() {
    Scaffold(
        containerColor = AppTheme.colorScheme.backgroundPrimary,
        topBar = {
            AppTopBar(
                text = R.string.liveness_identomat_camera_deny_settings,
                onClickBack = {

                }
            )
        }
    ) {
        Column(Modifier.padding(it)) {
            ItemSettings(img = R.drawable.ic_filter_24_regular, text = R.string.inbox_main_title, onClick = {})
            ItemSettings(img = R.drawable.ic_filter_24_regular, text = R.string.profile_theme_language_title, onClick = {})
            ItemSettings(img = R.drawable.ic_document_contract_24_regular, text = R.string.contracts_main_title, onClick = {})
            ItemSettings(img = R.drawable.ic_lock_closed_24_regular, text = R.string.profile_security_title, onClick = {})
            ItemSettings(img = R.drawable.ic_lock_closed_24_regular, text = R.string.profile_other_title , onClick = {})
        }
    }
}

@Composable
private fun ItemSettings(img: Int, text: Int, onClick: () -> Unit) {
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
        Icon(painter = painterResource(R.drawable.ic_chevron_right_24_regular), contentDescription = null, tint = AppTheme.colorScheme.borderSecondary)
    }
}
