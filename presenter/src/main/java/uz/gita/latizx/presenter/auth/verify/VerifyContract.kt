package uz.gita.latizx.presenter.auth.verify

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import uz.gita.latizx.comman.model.RecipientData
import uz.gita.latizx.presenter.auth.pin_code.PinCodeContract.UIIntent

interface VerifyContract {

    interface UiIntent {
        data class OpenHome(val code: String) : UiIntent
        data object OpenPrevScreen : UiIntent
        data object ResendCode : UiIntent
        data object CloseScreen : UiIntent
        data object DismissErrorDialog : UiIntent
    }

    data class UiState(
        val time: String = "00:00",
        val timeStarted: Boolean = false,
    )

    interface VerifyViewModel {
        val uiState: StateFlow<UiState>
        val sideEffect: Channel<SideEffect>
        fun onEventDispatcher(uiIntent: UiIntent)
    }

    data class SideEffect(
        val showErrorDialog: Boolean = false,
        val showLoading: Boolean = false,
    )

    interface Directions {
        suspend fun navigateToBack()
        suspend fun navigateToPinCode()
        suspend fun navigateToSuccess(recipientData: RecipientData)
        suspend fun closeScreen()
        suspend fun closeTransferVerifyScreen()
    }
}