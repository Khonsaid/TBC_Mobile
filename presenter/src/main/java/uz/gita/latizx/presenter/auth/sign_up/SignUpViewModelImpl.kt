package uz.gita.latizx.presenter.auth.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.comman.isValidDate
import uz.gita.latizx.comman.isValidName
import uz.gita.latizx.comman.isValidPassword
import uz.gita.latizx.comman.isValidPhoneNumber
import uz.gita.latizx.usecase.auth.SignUpUseCase
import javax.inject.Inject
import kotlin.onFailure
import kotlin.onSuccess

@HiltViewModel
class SignUpViewModelImpl @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val directions: SignUpContract.Directions,
) : SignUpContract.SignUpViewModel, ViewModel() {
    override val uiState =
        MutableStateFlow<SignUpContract.UIState>(SignUpContract.UIState())
    override val sideEffect = Channel<SignUpContract.SideEffect>()
    val _sideEffect = sideEffect.receiveAsFlow()

    override fun onEventDispatcher(uiIntent: SignUpContract.UIIntent) {
        when (uiIntent) {
            is SignUpContract.UIIntent.OpenVerify -> {
                when {
                    !uiIntent.firstName.isValidName() -> {
                        viewModelScope.launch {
                            sideEffect.send(SignUpContract.SideEffect(1, showErrorDialog = true))
                        }
                    }

                    !uiIntent.lastName.isValidName() -> {
                        viewModelScope.launch {
                            sideEffect.send(SignUpContract.SideEffect(2, showErrorDialog = true))
                        }
                    }

                    !uiIntent.bornDate.isValidDate() -> {
                        viewModelScope.launch {
                            sideEffect.send(SignUpContract.SideEffect(3, showErrorDialog = true))
                        }
                    }

                    !uiIntent.phone.isValidPhoneNumber() -> {
                        viewModelScope.launch {
                            sideEffect.send(SignUpContract.SideEffect(4, showErrorDialog = true))
                        }
                    }

                    !uiIntent.password.isValidPassword() -> {
                        viewModelScope.launch {
                            sideEffect.send(SignUpContract.SideEffect(5, showErrorDialog = true))
                        }
                    }

                    else -> {
                        reduce { it.copy(showLoading = true) }
                        signUpUseCase.invoke(
                            phone = uiIntent.phone, password = uiIntent.password, firstName = uiIntent.firstName,
                            lastName = uiIntent.lastName, bornDate = uiIntent.bornDate.toString(), gender = "0"
                        ).onEach { result ->
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
                                reduce { it.copy(showLoading = false) }
                                viewModelScope.launch { sideEffect.send(SignUpContract.SideEffect(0)) }
                            }
                        }.launchIn(viewModelScope)
                    }
                }
            }

            is SignUpContract.UIIntent.ShowModeInfoDialog -> viewModelScope.launch { sideEffect.send(SignUpContract.SideEffect(showMoreInfo = true)) }
            is SignUpContract.UIIntent.DismissErrorDialog -> viewModelScope.launch { sideEffect.send(SignUpContract.SideEffect(showErrorDialog = false)) }
            is SignUpContract.UIIntent.DismissMoreInfoDialog -> viewModelScope.launch { sideEffect.send(SignUpContract.SideEffect(showMoreInfo = false)) }
            is SignUpContract.UIIntent.OpenPrev -> viewModelScope.launch { directions.navigateToBack() }
        }
    }

    private fun reduce(block: (SignUpContract.UIState) -> SignUpContract.UIState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }
}