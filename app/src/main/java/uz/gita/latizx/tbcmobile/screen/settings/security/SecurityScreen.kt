package uz.gita.latizx.tbcmobile.screen.settings.security

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.latizx.presenter.settings.security.SecurityContract
import uz.gita.latizx.presenter.settings.security.SecurityViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.components.items.ItemSettings
import uz.gita.latizx.tbcmobile.ui.components.topbar.AppTopBar
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

class SecurityScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: SecurityContract.SecurityViewModel = getViewModel<SecurityViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState()
        SecurityScreenContent(uiState = uiState, viewModel::onEventDispatcher)

    }
}

@Composable
private fun SecurityScreenContent(
    uiState: State<SecurityContract.UIState> = remember { mutableStateOf(SecurityContract.UIState()) },
    eventDispatcher: (SecurityContract.UIIntent) -> Unit = {},
) {
    Scaffold(
        containerColor = AppTheme.colorScheme.backgroundPrimary,
        topBar = {
            AppTopBar(
                text = R.string.liveness_identomat_camera_deny_settings,
                onClickBack = {
                    eventDispatcher.invoke(SecurityContract.UIIntent.OpenPrevScreen)
                }
            )
        }
    ) {
        Column(
            Modifier
                .padding(it)
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .padding(start = 16.dp),
                text = stringResource(R.string.user_security_easy_access),
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colorScheme.textPrimary
            )
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
            Row(
                Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(R.drawable.ic_biometric_24_regular), contentDescription = null, tint = AppTheme.colorScheme.iconAccentCyan, modifier = Modifier.size(32.dp))
                Spacer(Modifier.width(24.dp))
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.user_security_biometric_title),
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colorScheme.textPrimary
                )
                Switch(
                    checked = uiState.value.statusBiometric,
                    onCheckedChange = {
                        eventDispatcher(SecurityContract.UIIntent.SwitchBiometric)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = AppTheme.colorScheme.iconAccentCyan,
                        checkedTrackColor = AppTheme.colorScheme.backgroundAccentCoolGray,
                        uncheckedThumbColor = AppTheme.colorScheme.backgroundPrimary,
                        uncheckedTrackColor = AppTheme.colorScheme.backgroundAccentCoolGray,
                        uncheckedBorderColor = AppTheme.colorScheme.iconAccentCyan.copy(alpha = 0f)
                    )
                )
                Spacer(Modifier.width(8.dp))
            }
            Text(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .padding(vertical = 8.dp),
                text = stringResource(R.string.user_security_biometric_description),
                style = AppTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400),
                color = AppTheme.colorScheme.textPrimary
            )
            ItemSettings(
                img = R.drawable.ic_lock_closed_24_regular,
                text = R.string.user_security_change_password,
                onClick = {
                    eventDispatcher(SecurityContract.UIIntent.ChangePassword)
                }
            )
        }
    }
}