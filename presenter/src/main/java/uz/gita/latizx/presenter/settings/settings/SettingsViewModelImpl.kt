package uz.gita.latizx.presenter.settings.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.usecase.settings.SettingsUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModelImpl @Inject constructor(
    private val directions: SettingsContract.Directions,
    private val settingsUseCase: SettingsUseCase,
) : SettingsContract.SettingsViewModel, ViewModel() {
    override val uiState = MutableStateFlow<SettingsContract.UIState>(SettingsContract.UIState())
    override val sideEffect = Channel<SettingsContract.SideEffect>()
    val _sideEffect = sideEffect.receiveAsFlow()

    override fun onEventDispatcher(uiIntent: SettingsContract.UIIntent) {
        when (uiIntent) {
            is SettingsContract.UIIntent.OpenPrev -> viewModelScope.launch { directions.navigateToPrev() }
            is SettingsContract.UIIntent.OpenSecurity -> viewModelScope.launch { directions.navigateToSecurity() }
            is SettingsContract.UIIntent.OpenGeneralSettingsScreen -> viewModelScope.launch { directions.navigateToGeneralSettingsScreenIntro() }
            is SettingsContract.UIIntent.Logout -> viewModelScope.launch {
                sideEffect.send(SettingsContract.SideEffect(showLogoutDialog = false))
                settingsUseCase.logOut()
                directions.navigateToIntro()
            }

            is SettingsContract.UIIntent.ShowLogoutDialog -> viewModelScope.launch { sideEffect.send(SettingsContract.SideEffect(showLogoutDialog = true)) }
            is SettingsContract.UIIntent.DismissDialog -> viewModelScope.launch { sideEffect.send(SettingsContract.SideEffect(showLogoutDialog = false)) }
        }
    }
}