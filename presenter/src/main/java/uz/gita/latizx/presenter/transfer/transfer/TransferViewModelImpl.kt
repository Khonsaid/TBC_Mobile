package uz.gita.latizx.presenter.transfer.transfer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.comman.model.CardsData
import uz.gita.latizx.comman.model.RecipientData
import uz.gita.latizx.usecase.card.GetCardsUseCase
import uz.gita.latizx.usecase.transfer.GetFreeUseCase

@HiltViewModel(assistedFactory = TransferViewModelImpl.Factory::class)
class TransferViewModelImpl @AssistedInject constructor(
    @Assisted private val recipientData: RecipientData,
    private val directions: TransferContract.Directions,
    private val getCardsUseCase: GetCardsUseCase,
    private val feeUseCase: GetFreeUseCase,
) : TransferContract.TransferViewModel, ViewModel() {
    override val uiState = MutableStateFlow<TransferContract.UIState>(
        TransferContract.UIState(panRecipient = recipientData.recipientPan, nameRecipient = recipientData.recipientName)
    )
    override val sideEffect = Channel<TransferContract.SideEffect>()
    val _sideEffect = sideEffect.receiveAsFlow()
    private val transferSum: StringBuilder = StringBuilder()
    private var cardList: List<CardsData> = emptyList()
    private var cardIndex = 0

    @AssistedFactory
    interface Factory {
        fun onCreate(recipientData: RecipientData): TransferViewModelImpl
    }

    init {
        getCards()
    }

    override fun onEventDispatcher(uiIntent: TransferContract.UIIntent) {
        when (uiIntent) {
            is TransferContract.UIIntent.OpenMoneyScreen -> viewModelScope.launch { directions.navigateToMoney() }
            is TransferContract.UIIntent.OpenRecipientScreen -> viewModelScope.launch { directions.navigateToRecipient() }
            is TransferContract.UIIntent.TransferSum -> {
                checkInputSum(uiIntent.sum)
            }

            is TransferContract.UIIntent.HideTextDialog -> viewModelScope.launch { sideEffect.send(TransferContract.SideEffect(showDialog = false)) }

            is TransferContract.UIIntent.OpenFeeScreen -> {
                if (uiState.value.sum.toInt() < 1000) {
                    viewModelScope.launch { sideEffect.send(TransferContract.SideEffect(showDialog = true, message = 1)) }
                } else if(uiState.value.sum.toInt() > cardList[cardIndex].amount){
                    viewModelScope.launch { sideEffect.send(TransferContract.SideEffect(showDialog = true, message = 2)) }
                }else {
                    feeUseCase.invoke(
                        amount = uiState.value.sum.toInt(),
                        receiver = recipientData.recipientPan,
                        senderId = "6"//cardList[0].id.toString()
                    ).onEach { result ->
                        result.onSuccess { feeData ->
                            directions.navigateToFee(recipientData, feeData, cardList[cardIndex].id.toString())
                        }
                        result.onFailure {
                            Log.d("TTT", "fail: ${it.message}")
//                        sideEffect.send(TransferContract.SideEffect(message = ))
                        }
                    }.launchIn(viewModelScope)
                }
            }

            is TransferContract.UIIntent.ClickRemove -> {
                if (transferSum.isNotEmpty()) {
                    transferSum.deleteCharAt(transferSum.length - 1)
                    val updatedSum = if (transferSum.isEmpty()) "0" else transferSum.toString()
                    reduce { it.copy(sum = updatedSum) }
                }
            }

            is TransferContract.UIIntent.ShowSelectCardBottomSheet -> {
                reduce { it.copy(cardList = cardList) }
                viewModelScope.launch { sideEffect.send(TransferContract.SideEffect(isBottomSheetVisible = true)) }
            }

            is TransferContract.UIIntent.HideSelectCardBottomSheet -> viewModelScope.launch { sideEffect.send(TransferContract.SideEffect(isBottomSheetVisible = false)) }

            is TransferContract.UIIntent.SelectCard -> {
                cardIndex = uiIntent.cardIndex
                reduce { it.copy(balance = cardList[uiIntent.cardIndex].amount.toString()) }
                reduce { it.copy(name = cardList[uiIntent.cardIndex].name.toString()) }
            }
        }
    }

    private fun checkInputSum(sum: String) {
        if (transferSum.length > 8) return
        if (sum == "0" && uiState.value.sum == "0") return

        transferSum.append(sum)
        reduce { it.copy(sum = transferSum.toString()) }
    }

    private fun getCards() {
        reduce { it.copy(showLoading = true) }
        getCardsUseCase.invoke().onEach { result ->
            result.onSuccess { list ->
                cardList = list
                reduce { it.copy(balance = list[cardIndex].amount.toString()) }
                reduce { it.copy(name = list[cardIndex].name.toString()) }
                reduce { it.copy(showLoading = false) }
            }
            result.onFailure {
                reduce { it.copy(showLoading = false) }
//                viewModelScope.launch { sideEffect.send(TransferContract.SideEffect(it.message)) }
            }
        }.launchIn(viewModelScope)
    }

    private inline fun reduce(block: (TransferContract.UIState) -> TransferContract.UIState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }
}