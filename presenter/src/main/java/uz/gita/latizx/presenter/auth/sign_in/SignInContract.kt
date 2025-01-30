package uz.gita.latizx.presenter.auth.sign_in

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import uz.gita.latizx.presenter.auth.sign_in.SignInContract.UiIntent

interface SignInContract {

    sealed interface UiIntent {
        data object OpenPrevScreen : UiIntent
        data class OpenVerifyScreen(val password: String, val phone: String) : UiIntent
    }

    data class SideEffect(val message: Int)

    data class UiState(
        val message: String="",
        val showLoading: Boolean = false,
    )

    interface SignInViewModel {
        val uiState: StateFlow<UiState>
        val sideEffect: Channel<SideEffect>
        fun onEventDispatcher(uiIntent: UiIntent)
    }

    interface Directions {
        suspend fun navigateToVerify(phoneNumber: String, password: String)
        suspend fun navigateToBAck()
    }
}