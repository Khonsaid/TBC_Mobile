package uz.gita.latizx.presenter.home.settings

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow

interface SettingsContract {

    sealed interface UIIntent {
        data object OpenPrev : UIIntent
        data object Logout : UIIntent
        data object ShowLogoutDialog : UIIntent
        data object DismissDialog : UIIntent
    }

    data class SideEffect(val showLogoutDialog: Boolean)

    data class UIState(val message: Boolean = false)

    interface SettingsViewModel {
        val uiState: StateFlow<UIState>
        val sideEffect: Channel<SideEffect>
        fun onEventDispatcher(uiIntent: UIIntent)
    }

    interface Directions {
        suspend fun navigateToPrev()
        suspend fun navigateToIntro()
    }
}