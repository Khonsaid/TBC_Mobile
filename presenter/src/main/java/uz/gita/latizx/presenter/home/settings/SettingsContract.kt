package uz.gita.latizx.presenter.home.settings

import kotlinx.coroutines.flow.StateFlow

interface SettingsContract {

    sealed interface UIIntent {
        data object OpenPrev : UIIntent
    }

    data class UIState(val showLoading: Boolean = false)

    interface SettingsViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(uiIntent: UIIntent)
    }

    interface Directions {
        suspend fun navigateToPrev()
    }
}