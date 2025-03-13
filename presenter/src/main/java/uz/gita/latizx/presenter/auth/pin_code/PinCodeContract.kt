package uz.gita.latizx.presenter.auth.pin_code

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow

interface PinCodeContract {

    sealed interface UIIntent {
        data object OpenSignInScreen : UIIntent
        data object OpenIntroScreen : UIIntent
        data object BiometricSuccess : UIIntent
        data class ClickNum(val code: String) : UIIntent
        data object ClickRemove : UIIntent
        data object DismissErrorDialog : UIIntent
        data class UpdateCodeArray(val updatedCodeArray: List<Boolean>) : UIIntent
    }

    data class SideEffect(
        val showErrorDialog: Boolean = false,
        val showLoading: Boolean = false,
    )

    data class UiState(
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