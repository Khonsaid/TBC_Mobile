package uz.gita.latizx.presenter.auth.verify

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.comman.enums.VerifyEnum
import uz.gita.latizx.comman.formatTime
import uz.gita.latizx.comman.model.RecipientData
import uz.gita.latizx.comman.model.TransferVerifyData
import uz.gita.latizx.comman.model.VerifyData
import uz.gita.latizx.usecase.auth.SignInResendUseCase
import uz.gita.latizx.usecase.auth.SignInVerifyUseCase
import uz.gita.latizx.usecase.auth.SignUpResendUseCase
import uz.gita.latizx.usecase.auth.SignUpVerifyUseCase
import uz.gita.latizx.usecase.transfer.TransferResendUseCase
import uz.gita.latizx.usecase.transfer.TransferVerifyUseCase

@HiltViewModel(assistedFactory = VerifyViewModelImpl.Factory::class)
class VerifyViewModelImpl @AssistedInject constructor(
    @Assisted private val verifyEnum: VerifyEnum,
    @Assisted private val data: VerifyData?,
    @Assisted private val transferVerifyData: TransferVerifyData?,
    @Assisted private val recipientData: RecipientData?,
    private val signInVerifyUseCase: SignInVerifyUseCase,
    private val signUpVerifyUseCase: SignUpVerifyUseCase,
    private val signInResendUseCase: SignInResendUseCase,
    private val signUpResendUseCase: SignUpResendUseCase,
    private val transferVerifyUseCase: TransferVerifyUseCase,
    private val transferResendUseCase: TransferResendUseCase,
    private val directions: VerifyContract.Directions,
) : VerifyContract.VerifyViewModel, ViewModel() {
    override val uiState = MutableStateFlow<VerifyContract.UiState>(VerifyContract.UiState())
    override val sideEffect = Channel<VerifyContract.SideEffect>()
    var _sideEffect = sideEffect.receiveAsFlow()
    private val timer = 60

    @AssistedFactory
    interface Factory {
        fun onCreate(
            verifyEnum: VerifyEnum,
            data: VerifyData?,
            transferVerifyData: TransferVerifyData?,
            recipientData: RecipientData?,
        ): VerifyViewModelImpl
    }

    init {
        startTimer()
    }

    override fun onEventDispatcher(uiIntent: VerifyContract.UiIntent) {
        when (uiIntent) {
            is VerifyContract.UiIntent.CloseScreen -> viewModelScope.launch {
                if (VerifyEnum.Transfer == verifyEnum) directions.closeTransferVerifyScreen()
                else directions.closeScreen()
            }
            is VerifyContract.UiIntent.DismissErrorDialog-> viewModelScope.launch{ sideEffect.send(VerifyContract.SideEffect(showErrorDialog = false)) }

            is VerifyContract.UiIntent.OpenPrevScreen -> viewModelScope.launch { directions.navigateToBack() }
            is VerifyContract.UiIntent.OpenHome -> {
                if (uiIntent.code.isNotEmpty()) {
                    when (verifyEnum) {
                        VerifyEnum.SigIn -> {
                            viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showLoading = true)) }
                            signInVerifyUseCase.invoke(uiIntent.code).onEach { result ->
                                result.onSuccess {
                                    Log.d("TTT", "onEventDispatcher: result.onSuccess")
                                    viewModelScope.launch { directions.navigateToPinCode() }
                                    viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showLoading = false)) }
                                }
                                result.onFailure {
                                    Log.d("TTT", "onEventDispatcher: result.onFailure")
                                    viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showLoading = false)) }
                                    viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showErrorDialog = true)) }
                                }
                            }.launchIn(viewModelScope)
                        }

                        VerifyEnum.SignUp -> {
                            viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showLoading = true)) }
                            signUpVerifyUseCase.invoke(uiIntent.code).onEach { result ->
                                result.onSuccess {
                                    viewModelScope.launch { directions.navigateToPinCode() }
                                    viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showLoading = false)) }
                                }
                                result.onFailure {
                                    viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showLoading = false)) }
                                    viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showErrorDialog = true)) }
                                }
                            }.launchIn(viewModelScope)
                        }

                        VerifyEnum.Transfer -> {
                            viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showLoading = true)) }
                            transferVerifyUseCase.invoke(uiIntent.code).onEach { result ->
                                result.onSuccess {
                                    viewModelScope.launch {
                                        if (recipientData != null) {
                                            directions.navigateToSuccess(recipientData = recipientData)
                                        }
                                        viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showLoading = false)) }
                                    }
                                }
                                result.onFailure {
                                    viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showLoading = false)) }
                                    viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showErrorDialog = true)) }
                                }
                            }.launchIn(viewModelScope)
                        }
                    }
                }
            }

            is VerifyContract.UiIntent.ResendCode -> {
                startTimer()
                when (verifyEnum) {
                    VerifyEnum.SigIn -> {
                        if (data != null) {
                            viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showLoading = true)) }
                            signInResendUseCase.invoke(password = data.password!!, phoneNumber = data.phone).onEach { result ->
                                result.onSuccess {
                                    viewModelScope.launch { directions.navigateToPinCode() }
                                    viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showLoading = false)) }
                                }
                                result.onFailure {
                                    viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showLoading = false)) }
                                    viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showErrorDialog = true)) }
                                }
                            }.launchIn(viewModelScope)
                        }
                    }

                    VerifyEnum.SignUp -> {
                        viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showLoading = true)) }
                        signUpResendUseCase.invoke().onEach { result ->
                            result.onSuccess {
                                viewModelScope.launch { directions.navigateToPinCode() }
                                viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showLoading = false)) }
                            }
                            result.onFailure {
                                viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showLoading = false)) }
                                viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showErrorDialog = true)) }
                            }
                        }.launchIn(viewModelScope)
                    }

                    VerifyEnum.Transfer -> {
                        if (transferVerifyData != null) {
                            viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showLoading = true)) }
                            transferResendUseCase.invoke(transferVerifyData).onEach { result ->
                                result.onSuccess {
                                    viewModelScope.launch {
                                        if (recipientData != null) {
                                            directions.navigateToSuccess(recipientData = recipientData)
                                        }
                                        viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showLoading = false)) }
                                    }
                                }
                                result.onFailure {
                                    viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showLoading = false)) }
                                    viewModelScope.launch { sideEffect.send(VerifyContract.SideEffect(showErrorDialog = true)) }
                                }
                            }.launchIn(viewModelScope)
                        }
                    }
                }
            }

            else -> viewModelScope.launch { directions.navigateToPinCode() }
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            var time = timer
            for (second in time downTo 0) {
                time = second
                reduce { it.copy(time = time.formatTime()) }
                if (!uiState.value.timeStarted) reduce { it.copy(timeStarted = true) }
                delay(1000)
                if (time <= 0) reduce { it.copy(timeStarted = false) }
            }
        }
    }

    private inline fun reduce(block: (VerifyContract.UiState) -> VerifyContract.UiState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }
}