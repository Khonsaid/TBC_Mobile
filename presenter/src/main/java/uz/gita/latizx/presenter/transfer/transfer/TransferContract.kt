package uz.gita.latizx.presenter.transfer.transfer

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import uz.gita.latizx.comman.model.CardsData
import uz.gita.latizx.comman.model.GetFeeData
import uz.gita.latizx.comman.model.RecipientData

interface TransferContract {
    sealed interface UIIntent {
        object OpenRecipientScreen : UIIntent
        object OpenMoneyScreen : UIIntent
        object OpenFeeScreen : UIIntent
        object ClickRemove : UIIntent
        object HideSelectCardBottomSheet : UIIntent
        object HideTextDialog : UIIntent
        object ShowSelectCardBottomSheet : UIIntent
        data class SelectCard(val cardIndex: Int) : UIIntent
        data class TransferSum(val sum: String) : UIIntent
    }

    data class UIState(
        val showCards: String = "",
        val sum: String = "0",
        val balance: String = "",
        val name: String = "",
        val panRecipient: String = "",
        val nameRecipient: String = "",
        val cardList: List<CardsData> = emptyList(),
        val showLoading: Boolean = false,
    )

    data class SideEffect(
        val isBottomSheetVisible: Boolean = false,
        val showDialog: Boolean = false,
        val message: Int = 0,
    )

    interface TransferViewModel {
        val uiState: StateFlow<UIState>
        val sideEffect: Channel<SideEffect>
        fun onEventDispatcher(uiIntent: UIIntent)
    }

    interface Directions {
        suspend fun navigateToRecipient()
        suspend fun navigateToFee(recipientData: RecipientData, getFeeData: GetFeeData, senderId: String)
        suspend fun navigateToMoney()
    }
}