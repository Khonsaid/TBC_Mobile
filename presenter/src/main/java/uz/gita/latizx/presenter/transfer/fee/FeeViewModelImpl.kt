package uz.gita.latizx.presenter.transfer.fee

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
import uz.gita.latizx.comman.model.GetFeeData
import uz.gita.latizx.comman.model.RecipientData
import uz.gita.latizx.comman.model.TransferVerifyData

import uz.gita.latizx.usecase.transfer.TransferUseCase
import kotlin.Int

@HiltViewModel(assistedFactory = FeeViewModelImpl.Factory::class)
class FeeViewModelImpl @AssistedInject constructor(
    @Assisted private val recipientData: RecipientData,
    @Assisted private val getFeeData: GetFeeData,
    @Assisted private val senderId: String,
    private val directions: FeeContract.Directions,
    private val transferUseCase: TransferUseCase,
) : FeeContract.FeeViewModel, ViewModel() {

    override val uiState = MutableStateFlow<FeeContract.UIState>(
        FeeContract.UIState(
            sum = calculateOriginalValue(getFeeData.amount, getFeeData.fee).toString(),
            fee = calculateFee(
                calculateOriginalValue(getFeeData.amount, getFeeData.fee),
                getFeeData.fee
            ).toString(),
            panRecipient = recipientData.recipientPan,
            nameRecipient = recipientData.recipientName
        )
    )
    override val sideEffect = Channel<FeeContract.SideEffect>()
    val _sideEffect = sideEffect.receiveAsFlow()

    @AssistedFactory
    interface Factory {
        fun onCreate(recipientData: RecipientData, getFeeData: GetFeeData, senderId: String): FeeViewModelImpl
    }

    override fun onEventDispatcher(uiIntent: FeeContract.UIIntent) {
        when (uiIntent) {
            is FeeContract.UIIntent.OpenMoneyScreen -> viewModelScope.launch { directions.navigateToMoney() }
            is FeeContract.UIIntent.OpenTransferScreen -> viewModelScope.launch { directions.navigateToTransfer() }
            is FeeContract.UIIntent.ClickConfirmation -> viewModelScope.launch {
                reduce { it.copy(showLoading = true) }
                transferUseCase.invoke(
                        amount = calculateOriginalValue(getFeeData.amount, getFeeData.fee),
                        receiverPan = recipientData.recipientPan,
                        senderId = senderId,
                        type = "third-card"
                ).onEach { result ->
                    result.onSuccess {
                        directions.navigateToVerify(
                            transferVerifyData = TransferVerifyData(
                                amount = calculateOriginalValue(getFeeData.amount, getFeeData.fee),
                                receiverPan = recipientData.recipientPan,
                                senderId = senderId,
                                type = "third-card"
                            ),
                            recipientData = recipientData
                        )
                        reduce { it.copy(showLoading = false) }
                    }
                    result.onFailure {
                        Log.d("TTT", "onEventDispatcher: $it")
//                        sideEffect.send(FeeContract.SideEffect(R.string.default_error_msg))
                        reduce { it.copy(showLoading = false) }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private inline fun reduce(block: (FeeContract.UIState) -> FeeContract.UIState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }

    private fun calculateOriginalValue(y: Int, p: Int): Int = (y * 100 / (100 + p))

    private fun calculateFee(amount: Int, feePercentage: Int): Int = (amount * feePercentage.toDouble() / 100).toInt()
}