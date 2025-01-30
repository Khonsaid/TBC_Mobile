package uz.gita.latizx.presenter.splash

import kotlinx.coroutines.flow.StateFlow

interface SplashContract {
    sealed interface UIIntent {
        object OpenNextScreen : UIIntent
    }

    data object UIState

    interface SplashViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(uiIntent: UIIntent)
    }

    interface Directions {
        suspend fun navigateToIntro()
        suspend fun navigateToPinCode()
    }
}