package uz.gita.latizx.presenter.auth.pin_code

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.usecase.auth.PinCodeUseCase
import uz.gita.latizx.usecase.settings.SettingsUseCase
import javax.inject.Inject

@HiltViewModel
class PinCodeViewModelImpl @Inject constructor(
    private val directions: PinCodeContract.Directions,
    private val settingsUseCase: SettingsUseCase,
    private val pinCodeUseCase: PinCodeUseCase,
) : PinCodeContract.PinCodeViewModel, ViewModel() {

    override val uiState = MutableStateFlow<PinCodeContract.UiState>(PinCodeContract.UiState())
    override val sideEffect = Channel<PinCodeContract.SideEffect>()
    val _sideEffect = sideEffect.receiveAsFlow()
    private val codeArray: Array<String> = arrayOf("", "", "", "")

    init {
        reduce { it.copy(statusBiometricAuth = settingsUseCase.getBiometricStatus()) }
    }

    override fun onEventDispatcher(uiIntent: PinCodeContract.UIIntent) {
        when (uiIntent) {
            is PinCodeContract.UIIntent.UpdateCodeArray -> {
                reduce { it.copy(codeArray = uiIntent.updatedCodeArray) }
            }

            is PinCodeContract.UIIntent.ClickNum -> {
                val emptyIndex = uiState.value.codeArray.indexOfFirst { !it }
                if (emptyIndex != -1) {
                    val updatedCodeArray = uiState.value.codeArray.toMutableList()
                    updatedCodeArray[emptyIndex] = true // To'ldirilgan joy sifatida belgilash
                    codeArray[emptyIndex] = uiIntent.code
                    reduce { it.copy(codeArray = updatedCodeArray) }

                    if (emptyIndex == 3) {
                        viewModelScope.launch {
                            viewModelScope.launch { sideEffect.send(PinCodeContract.SideEffect(showLoading = true)) }
                            val pinCodeResult = pinCodeUseCase.getPinCode()
                            pinCodeResult.onSuccess { savedPinCode ->
                                viewModelScope.launch { sideEffect.send(PinCodeContract.SideEffect(showLoading = false)) }
                                if (!uiIntent.setPinCode && savedPinCode.isNotEmpty()) {
                                    if (codeArray.joinToString() == savedPinCode) viewModelScope.launch { directions.navigateToHome() }
                                    else viewModelScope.launch {
                                        sideEffect.send(PinCodeContract.SideEffect(showErrorDialog = true))
//                                        sideEffect.send(PinCodeContract.SideEffect())
                                    }
                                } else if (uiIntent.setPinCode || savedPinCode.isEmpty()) {
                                    pinCodeUseCase.setPinCode(codeArray.joinToString())
                                    viewModelScope.launch { directions.navigateToHome() }
                                }

                            }.onFailure {
//                                sideEffect.send(PinCodeContract.SideEffect(R.string.signing_change_password_dialog_title))
                            }
                        }
                    }
                }
            }

            is PinCodeContract.UIIntent.DismissErrorDialog -> {
                viewModelScope.launch {
                    sideEffect.send(PinCodeContract.SideEffect(showErrorDialog = false))
                }
            }

            is PinCodeContract.UIIntent.ClickRemove -> {
                val lastIndex = uiState.value.codeArray.indexOfLast { it }
                if (lastIndex != -1) {
                    val updatedCodeArray = uiState.value.codeArray.toMutableList()
                    updatedCodeArray[lastIndex] = false // Oxirgi indikatorni bo'shatish
                    codeArray[lastIndex] = ""
                    reduce { it.copy(codeArray = updatedCodeArray) }
                }
            }

            is PinCodeContract.UIIntent.Logout -> {
                viewModelScope.launch {
                    sideEffect.send(PinCodeContract.SideEffect(showLogoutDialog = false))
                    pinCodeUseCase.setPinCode("")
                    directions.navigateToInto()
                }
            }

            is PinCodeContract.UIIntent.ShowLogoutDialog -> viewModelScope.launch { sideEffect.send(PinCodeContract.SideEffect(showLogoutDialog = true)) }
            is PinCodeContract.UIIntent.DismissDialog -> viewModelScope.launch { sideEffect.send(PinCodeContract.SideEffect(showLogoutDialog = false)) }
            is PinCodeContract.UIIntent.OpenSignInScreen -> viewModelScope.launch { directions.navigateToSignIn() }
            is PinCodeContract.UIIntent.BiometricSuccess -> viewModelScope.launch { directions.navigateToHome() }
        }
    }

    private inline fun reduce(block: (PinCodeContract.UiState) -> PinCodeContract.UiState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }
}