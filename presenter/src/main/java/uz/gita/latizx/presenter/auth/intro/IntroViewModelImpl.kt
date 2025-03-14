package uz.gita.latizx.presenter.auth.intro

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.comman.LocationHelper
import javax.inject.Inject

@HiltViewModel
class IntroViewModelImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val directions: IntroContract.Directions,
) : IntroContract.ViewModel, ViewModel() {

    override val uiState =
        MutableStateFlow<IntroContract.UiState>(IntroContract.UiState(LocationHelper.getLang()))

    override fun onEventDispatcher(uiIntent: IntroContract.UiIntent) {
        when (uiIntent) {
            is IntroContract.UiIntent.OpenSignInScreen -> viewModelScope.launch { directions.navigateToSignIn() }
            is IntroContract.UiIntent.OpenSignUpScreen -> viewModelScope.launch { directions.navigateToSignUp() }
            is IntroContract.UiIntent.OpenSupportScreen -> viewModelScope.launch { directions.navigateToSupport() }
            is IntroContract.UiIntent.ShowLanguageBottomSheet -> reduce {
                it.copy(isBottomSheetVisible = true)
            }

            is IntroContract.UiIntent.HideLanguageBottomSheet -> reduce {
                it.copy(isBottomSheetVisible = false)
            }
            is IntroContract.UiIntent.ResetRecreateFlag -> reduce {
                it.copy(shouldRecreateActivity = false)
            }

            is IntroContract.UiIntent.SaveLanguage -> {
                val shouldRecreateActivity = LocationHelper.getLang() != uiIntent.lang
                LocationHelper.setLocation(context, uiIntent.lang)
                reduce { it.copy(lang = LocationHelper.getLang(), isBottomSheetVisible = false, shouldRecreateActivity = shouldRecreateActivity) }
            }
        }
    }

    private inline fun reduce(block: (IntroContract.UiState) -> IntroContract.UiState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }
}