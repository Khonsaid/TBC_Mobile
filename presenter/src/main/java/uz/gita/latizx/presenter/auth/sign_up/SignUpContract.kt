package uz.gita.latizx.presenter.auth.sign_up

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow

interface SignUpContract {
    sealed interface UIIntent {
        data class OpenVerify(
            val firstName: String,
            val lastName: String,
            val bornDate: Long,
            val phone: String,
            val password: String
        ) : UIIntent

        data object OpenPrev : UIIntent
    }

    data class UIState(
        val message: String = "",
        val showLoading: Boolean = false,
    )

    interface SignUpViewModel {
        val uiState: StateFlow<UIState>
        val sideEffect: Channel<SideEffect>
        fun onEventDispatcher(intent: UIIntent)
    }

    interface Directions {
        suspend fun navigateToVerify(phoneNumber: String, password: String)
        suspend fun navigateToBack()
    }
    data class SideEffect(val msg: Int)
}