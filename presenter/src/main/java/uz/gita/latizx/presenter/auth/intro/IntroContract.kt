package uz.gita.latizx.presenter.auth.intro

import kotlinx.coroutines.flow.StateFlow

interface IntroContract {

    sealed interface UiIntent {
        data object OpenSignInScreen : UiIntent
        data object OpenSignUpScreen : UiIntent
        data object OpenSupportScreen : UiIntent
        data object ShowLanguageBottomSheet : UiIntent
        data object HideLanguageBottomSheet : UiIntent
        data object ResetRecreateFlag : UiIntent
        data class SaveLanguage(val lang: String) : UiIntent
    }

    data class UiState(
        val lang: String = "UZ",
        val shouldRecreateActivity: Boolean = false,
        val isBottomSheetVisible: Boolean = false
    )

    interface ViewModel {
        val uiState: StateFlow<UiState>
        fun onEventDispatcher(uiIntent: UiIntent)
    }

    interface Directions {
        suspend fun navigateToSignIn()
        suspend fun navigateToSignUp()
        suspend fun navigateToSupport()
    }
}