package uz.gita.latizx.tbcmobile.screen.settings.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.latizx.presenter.settings.settings.SettingsContract
import uz.gita.latizx.presenter.settings.settings.SettingsViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.components.dialog.ConfirmationDialog
import uz.gita.latizx.tbcmobile.ui.components.topbar.AppTopBar
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

class SettingsScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<SettingsViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState()
        SettingsScreenContent(uiState = uiState, viewModel::onEventDispatcher)

        var showLogoutDialog by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            viewModel._sideEffect.collect {
                showLogoutDialog = it.showLogoutDialog
            }
        }

        if (showLogoutDialog) {
            ConfirmationDialog(
                text = R.string.login_log_out_alert_message,
                textYesButton = R.string.login_yes,
                textNoButton = R.string.login_no,
                onClickYes = { viewModel.onEventDispatcher(SettingsContract.UIIntent.Logout) },
                onDismissRequest = { viewModel.onEventDispatcher(SettingsContract.UIIntent.DismissDialog) }
            )
        }
    }
}

@Composable
private fun SettingsScreenContent(
    uiState: State<SettingsContract.UIState> = remember { mutableStateOf(SettingsContract.UIState()) },
    eventDispatcher: (SettingsContract.UIIntent) -> Unit = {},
) {
    Scaffold(
        containerColor = AppTheme.colorScheme.backgroundPrimary,
        topBar = {
            AppTopBar(
                text = R.string.liveness_identomat_camera_deny_settings,
                onClickBack = {
                    eventDispatcher.invoke(SettingsContract.UIIntent.OpenPrev)
                }
            )
        }
    ) {
        Column(
            Modifier
                .padding(it)
                .padding(vertical = 24.dp)
        ) {
            ItemSettings(img = R.drawable.ic_notification, text = R.string.inbox_main_title, onClick = {})
            ItemSettings(img = R.drawable.ic_filter_24_regular, text = R.string.profile_theme_language_title, onClick = {
                eventDispatcher.invoke(SettingsContract.UIIntent.OpenGeneralSettingsScreen)
            })
            ItemSettings(img = R.drawable.ic_document_contract_24_regular, text = R.string.contracts_main_title, onClick = {})
            ItemSettings(img = R.drawable.ic_lock_closed_24_regular, text = R.string.profile_security_title, onClick = {})
            ItemSettings(img = R.drawable.ic_menu, text = R.string.profile_other_title, onClick = {})
            Spacer(Modifier.weight(1f))
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
                            eventDispatcher.invoke(SettingsContract.UIIntent.ShowLogoutDialog)
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.login_log_out),
                            style = AppTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = AppTheme.colorScheme.borderAccentRed
                        )
                        Icon(modifier = Modifier.size(24.dp), painter = painterResource(R.drawable.ic_quit), contentDescription = null, tint = AppTheme.colorScheme.borderAccentRed)
                    }
                }
            }
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
        Icon(modifier = Modifier.size(24.dp), painter = painterResource(R.drawable.ic_chevron_right_24_regular), contentDescription = null, tint = AppTheme.colorScheme.borderSecondary)
    }
}
