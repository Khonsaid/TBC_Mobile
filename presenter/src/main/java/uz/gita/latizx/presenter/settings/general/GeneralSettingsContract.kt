package uz.gita.latizx.presenter.settings.general

import kotlinx.coroutines.flow.StateFlow
import uz.gita.latizx.comman.ThemeMode

interface GeneralSettingsContract {
    sealed interface UIIntent {
        data object OpenPrevScreen : UIIntent
        data class SelectTheme(val theme: ThemeMode) : UIIntent
        data class SelectLang(val lang: String) : UIIntent
    }

    data class UIState(
        val currTheme: String = "",
        val currLang: String = "",
    )

    interface GeneralSettingsViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(uiIntent: UIIntent)
    }

    interface Directions {
        suspend fun navigateToBack()
    }
}