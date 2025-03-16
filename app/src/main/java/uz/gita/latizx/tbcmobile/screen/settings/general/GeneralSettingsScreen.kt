package uz.gita.latizx.tbcmobile.screen.settings.general

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.latizx.comman.ThemeMode
import uz.gita.latizx.presenter.settings.general.GeneralSettingsContract
import uz.gita.latizx.presenter.settings.general.GeneralSettingsViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.components.topbar.AppTopBar
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

class GeneralSettingsScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: GeneralSettingsContract.GeneralSettingsViewModel = getViewModel<GeneralSettingsViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState().value

        GeneralSettingsScreenContent(
            uiState = uiState,
            onEventDispatcher = viewModel::onEventDispatcher
        )
    }
}

@Composable
private fun GeneralSettingsScreenContent(
    uiState: GeneralSettingsContract.UIState,
    onEventDispatcher: (GeneralSettingsContract.UIIntent) -> Unit,
) {
    val currentTheme = uiState.currTheme
    val currentLang = uiState.currLang
    val context = LocalContext.current

    Scaffold(
        containerColor = AppTheme.colorScheme.backgroundPrimary,
        topBar = {
            AppTopBar(
                text = R.string.profile_theme_language_title,
                onClickBack = {
                    onEventDispatcher(GeneralSettingsContract.UIIntent.OpenPrevScreen)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(14.dp))

            // Theme Selection
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.settings_appearance),
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colorScheme.textPrimary
            )

            // Theme options
            ThemeOption(
                title = R.string.settings_light_mode,
                selected = currentTheme == ThemeMode.LIGHT.value,
                textTitle = null,
                onClick = {
                    onEventDispatcher(GeneralSettingsContract.UIIntent.SelectTheme(ThemeMode.LIGHT))
                    update(context)
                }
            )
            ThemeOption(
                title = R.string.settings_dark_mode,
                selected = currentTheme == ThemeMode.DARK.value,
                textTitle = null,
                onClick = {
                    onEventDispatcher(GeneralSettingsContract.UIIntent.SelectTheme(ThemeMode.DARK))
                    update(context)
                }
            )
            ThemeOption(
                title = R.string.settings_Auto_mode,
                selected = currentTheme == ThemeMode.SYSTEM.value,
                textTitle = null,
                onClick = {
                    onEventDispatcher(GeneralSettingsContract.UIIntent.SelectTheme(ThemeMode.SYSTEM))
                    update(context)
                }
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Theme Selection
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.settings_choose_language),
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colorScheme.textPrimary
            )

            // Theme options
            ThemeOption(
                title = null,
                textTitle = "O'zbek (Lotin)",
                selected = currentLang == "uz",
                onClick = {
                    onEventDispatcher(GeneralSettingsContract.UIIntent.SelectLang("uz"))
                    update(context)
                }
            )
            ThemeOption(
                title = null,
                textTitle = "Русский",
                selected = currentLang == "ru",
                onClick = {
                    onEventDispatcher(GeneralSettingsContract.UIIntent.SelectLang("ru"))
                    update(context)
                }
            )
        }
    }
}

private fun update(context: Context) {
    val activity = context as? android.app.Activity
    activity?.recreate()
}

@Composable
private fun ThemeOption(
    title: Int?,
    textTitle: String?,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = AppTheme.colorScheme.borderBrand,
                unselectedColor = AppTheme.colorScheme.borderBrand.copy(alpha = 0.6f)
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = if (title == null) textTitle ?: "" else stringResource(title),
            fontSize = 16.sp,
            color = if (selected) AppTheme.colorScheme.borderBrand else AppTheme.colorScheme.textPrimary
        )
    }
}