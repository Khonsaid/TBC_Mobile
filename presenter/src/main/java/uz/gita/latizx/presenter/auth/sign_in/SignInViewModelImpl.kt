package uz.gita.latizx.presenter.auth.sign_in

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.comman.isValidPassword
import uz.gita.latizx.comman.isValidPhoneNumber
import uz.gita.latizx.usecase.auth.SignInUseCase
import javax.inject.Inject

@HiltViewModel
class SignInViewModelImpl @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val directions: SignInContract.Directions,
) : SignInContract.SignInViewModel, ViewModel() {
    override val uiState = MutableStateFlow<SignInContract.UiState>(SignInContract.UiState())
    override val sideEffect = Channel<SignInContract.SideEffect>()
    val _sideEffect = sideEffect.receiveAsFlow()

    override fun onEventDispatcher(uiIntent: SignInContract.UiIntent) {
        when (uiIntent) {
            is SignInContract.UiIntent.OpenVerifyScreen -> {
                when {
                    !uiIntent.phone.isValidPhoneNumber() -> {
                        viewModelScope.launch {
//                            sideEffect.send(SignInContract.SideEffect(uiIntent.phone.isValidPhoneNumber().second))
                        }
                    }

                    !uiIntent.password.isValidPassword() -> {
                        viewModelScope.launch {
//                            sideEffect.send(SignInContract.SideEffect(uiIntent.password.isValidPassword().second))
                        }
                    }

                    else -> viewModelScope.launch {
                        reduce { it.copy(showLoading = true) }
                        signInUseCase.invoke(phone = uiIntent.phone, password = uiIntent.password).onEach { result ->
                            result.onSuccess {
                                reduce { it.copy(showLoading = false) }
                                viewModelScope.launch {
                                    directions.navigateToVerify(
                                        phoneNumber = uiIntent.phone,
                                        password = uiIntent.password
                                    )
                                }
                            }
                            result.onFailure {
                                it.message?.let {
                                    reduce { it.copy(showLoading = false) }
                                    Log.d("TTT", "onEventDispatcher: $it")
//                                    viewModelScope.launch { sideEffect.send(SignInContract.SideEffect(R.string.story_nothing_found)) }
                                }
                            }
                        }.launchIn(viewModelScope)
                    }
                }
            }

            is SignInContract.UiIntent.OpenPrevScreen -> viewModelScope.launch { directions.navigateToBAck() }
        }
    }

    private fun reduce(block: (SignInContract.UiState) -> SignInContract.UiState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }
}