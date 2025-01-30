package uz.gita.latizx.presenter.transfer.recipient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.usecase.transfer.GetCardOwnerByPanUseCase
import javax.inject.Inject

@HiltViewModel
class RecipientViewModelImpl @Inject constructor(
    private val directions: RecipientContract.Directions,
    private val getCardOwnerByPanUseCase: GetCardOwnerByPanUseCase,
) : RecipientContract.RecipientViewModel, ViewModel() {

    override val uiState = MutableStateFlow<RecipientContract.UIState>(RecipientContract.UIState())
    override val sideEffect = Channel<RecipientContract.SideEffect>()
    val _sideEffect = sideEffect.receiveAsFlow()

    //    private var pan = ""
    private var hasCardOwner = false

    override fun onEventDispatcher(uiIntent: RecipientContract.UIIntent) {
        when (uiIntent) {
            is RecipientContract.UIIntent.OpenMoneyScreen -> viewModelScope.launch { directions.navigateToMoney() }
            is RecipientContract.UIIntent.OpenTransferScreen -> {
                if (uiState.value.pan.length == 16 && hasCardOwner)
                    viewModelScope.launch {
                        directions.navigateToTransfer(
                            recipientCardNumber = uiState.value.pan,
                            recipientName = uiState.value.showRecipient
                        )
                    }
//                else
//                    viewModelScope.launch { sideEffect.send(RecipientContract.SideEffect(R.string.transfer_card_number_enter)) }
            }

            is RecipientContract.UIIntent.CheckCardNumber -> {
                reduce { it.copy(pan = uiIntent.cardNumber) }

                if (uiState.value.pan.length == 16) {
                    checkPan(uiState.value.pan)
                } else {
                    if (hasCardOwner) {
                        reduce { it.copy(showRecipient = "") }
                        hasCardOwner = false
                    }
                }
            }
        }
    }

    private fun checkPan(pan: String) {
        reduce { it.copy(showLoading = true) }
        getCardOwnerByPanUseCase.invoke(pan).onEach { result ->
            result.onSuccess { data ->
                hasCardOwner = true
                reduce { it.copy(showLoading = false) }
                reduce { it.copy(showRecipient = data.pan) }
            }
            result.onFailure {
                hasCardOwner = false
                reduce { it.copy(showLoading = false) }
                reduce { it.copy(showRecipient = "") }
                viewModelScope.launch {
//                    sideEffect.send(RecipientContract.SideEffect(R.string.card_component_address_not_found))
                }
            }
        }.launchIn(viewModelScope)
    }

    private inline fun reduce(block: (RecipientContract.UIState) -> RecipientContract.UIState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }
}