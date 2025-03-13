package uz.gita.latizx.presenter.auth.sign_up

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import uz.gita.latizx.presenter.auth.sign_in.SignInContract.UiIntent

interface SignUpContract {
    sealed interface UIIntent {
        data class OpenVerify(
            val firstName: String,
            val lastName: String,
            val bornDate: Long,
            val phone: String,
            val password: String
        ) : UIIntent
        data object ShowModeInfoDialog : UIIntent
        data object DismissErrorDialog : UIIntent
        data object DismissMoreInfoDialog : UIIntent
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
    data class SideEffect(
        val message: Int = -1,
        val showErrorDialog: Boolean = false,
        val showMoreInfo: Boolean = false,
    )
}