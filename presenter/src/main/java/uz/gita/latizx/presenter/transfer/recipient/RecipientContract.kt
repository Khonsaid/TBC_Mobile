package uz.gita.latizx.presenter.transfer.recipient

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow

interface RecipientContract {
    sealed interface UIIntent {
        object OpenTransferScreen : UIIntent
        object OpenMoneyScreen : UIIntent
        data class CheckCardNumber(val cardNumber: String) : UIIntent
    }

    data class UIState(
        val pan: String = "",
        val showRecipient: String = "",
        val showLoading: Boolean = false
    )

    data class SideEffect(val message: Int)

    interface RecipientViewModel {
        val uiState: StateFlow<UIState>
        val sideEffect: Channel<SideEffect>
        fun onEventDispatcher(uiIntent: UIIntent)
    }

    interface Directions {
        suspend fun navigateToTransfer(recipientCardNumber: String, recipientName: String)
        suspend fun navigateToMoney()
    }
}