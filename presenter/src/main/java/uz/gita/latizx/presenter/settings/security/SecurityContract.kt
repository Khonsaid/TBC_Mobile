package uz.gita.latizx.presenter.settings.security

import kotlinx.coroutines.flow.StateFlow

interface SecurityContract {
    sealed interface UIIntent {
        data object OpenPrevScreen : UIIntent
        data object SwitchBiometric : UIIntent
        data object ChangePassword : UIIntent
    }

    data class UIState(
        val statusBiometric: Boolean = true,
    )

    interface SecurityViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(uiIntent: UIIntent)
    }

    interface Directions {
        suspend fun navigateToBack()
        suspend fun navigateToChangePassword()
    }
}