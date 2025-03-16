package uz.gita.latizx.presenter.settings.security

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.usecase.settings.SettingsUseCase
import javax.inject.Inject

@HiltViewModel
class SecurityViewModelImpl @Inject constructor(
    private val settingsUseCase: SettingsUseCase,
    private val directions: SecurityContract.Directions,
) : SecurityContract.SecurityViewModel, ViewModel() {
    override val uiState = MutableStateFlow<SecurityContract.UIState>(SecurityContract.UIState())

    init {
        reduce { it.copy(statusBiometric = settingsUseCase.getBiometricStatus()) }
    }

    override fun onEventDispatcher(uiIntent: SecurityContract.UIIntent) {
        when (uiIntent) {
            is SecurityContract.UIIntent.ChangePassword -> viewModelScope.launch { directions.navigateToChangePassword() }
            is SecurityContract.UIIntent.OpenPrevScreen -> viewModelScope.launch { directions.navigateToBack() }
            is SecurityContract.UIIntent.SwitchBiometric -> {
                settingsUseCase.changeBiometricStatus()
                reduce { it.copy(statusBiometric = settingsUseCase.getBiometricStatus()) }
            }
        }
    }

    private inline fun reduce(block: (SecurityContract.UIState) -> SecurityContract.UIState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }
}