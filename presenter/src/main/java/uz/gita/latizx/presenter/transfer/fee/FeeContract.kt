package uz.gita.latizx.presenter.transfer.fee

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import uz.gita.latizx.comman.model.RecipientData
import uz.gita.latizx.comman.model.TransferVerifyData

interface FeeContract {
    sealed interface UIIntent {
        object OpenTransferScreen : UIIntent
        object OpenMoneyScreen : UIIntent
        object ClickConfirmation : UIIntent
    }

    data class UIState(
        val sum: String = "0",
        val fee: String = "",
        val panRecipient: String = "",
        val nameRecipient: String = "",
        val showLoading: Boolean = false,
    )

    data class SideEffect(val message: Int)

    interface FeeViewModel {
        val uiState: StateFlow<UIState>
        val sideEffect: Channel<SideEffect>
        fun onEventDispatcher(uiIntent: UIIntent)
    }

    interface Directions {
        suspend fun navigateToTransfer()
        suspend fun navigateToVerify(transferVerifyData: TransferVerifyData, recipientData: RecipientData)
        suspend fun navigateToMoney()
    }
}