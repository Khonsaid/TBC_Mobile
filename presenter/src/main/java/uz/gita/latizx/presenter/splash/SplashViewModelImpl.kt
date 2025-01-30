package uz.gita.latizx.presenter.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.usecase.auth.PinCodeUseCase
import javax.inject.Inject
import kotlin.text.isNotEmpty

@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val pinCode: PinCodeUseCase,
    private val directions: SplashContract.Directions,
) : SplashContract.SplashViewModel, ViewModel() {
    override val uiState = MutableStateFlow<SplashContract.UIState>(SplashContract.UIState)

    override fun onEventDispatcher(uiIntent: SplashContract.UIIntent) {
        when (uiIntent) {
            is SplashContract.UIIntent.OpenNextScreen -> {

                viewModelScope.launch{
                    pinCode.getPinCode().onSuccess {pincode->
                        if (pincode.isNotEmpty()) //&& pref.token.isNotEmpty())
                            viewModelScope.launch { directions.navigateToPinCode() }
                        else viewModelScope.launch { directions.navigateToIntro() }
                    }
                }
            }
        }
    }
}