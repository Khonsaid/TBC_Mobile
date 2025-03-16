package uz.gita.latizx.presenter.auth.pin_code

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow

interface PinCodeContract {

    sealed interface UIIntent {
        data object OpenSignInScreen : UIIntent
        data object Logout : UIIntent
        data object BiometricSuccess : UIIntent
        data object ClickRemove : UIIntent
        data object DismissErrorDialog : UIIntent
        data object ShowLogoutDialog : UIIntent
        data object DismissDialog : UIIntent
        data class ClickNum(val code: String, val setPinCode: Boolean) : UIIntent
        data class UpdateCodeArray(val updatedCodeArray: List<Boolean>) : UIIntent
    }

    data class SideEffect(
        val showErrorDialog: Boolean = false,
        val showLoading: Boolean = false,
        val showLogoutDialog: Boolean = false,
    )

    data class UiState(
        val statusBiometricAuth: Boolean = true,
        val message: String = "",
        val codeArray: List<Boolean> = listOf(false, false, false, false),
    )

    interface PinCodeViewModel {
        val uiState: StateFlow<UiState>
        val sideEffect: Channel<SideEffect>
        fun onEventDispatcher(uiIntent: UIIntent)
    }

    interface Directions {
        suspend fun navigateToHome()
        suspend fun navigateToInto()
        suspend fun navigateToSignIn()
    }
}