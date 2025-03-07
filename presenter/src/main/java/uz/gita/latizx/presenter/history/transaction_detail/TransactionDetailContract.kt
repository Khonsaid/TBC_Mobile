package uz.gita.latizx.presenter.history.transaction_detail

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import uz.gita.latizx.comman.model.HistoryItemsData

interface TransactionDetailContract {
    sealed interface UIIntent {
        data object OpenPrevScreen : UIIntent
        data object OpenHomeScreen : UIIntent
    }

    data class SideEffect(val message: String)

    data class UIState(
        val historyData: HistoryItemsData? = null,
    )

    interface TransactionDetailViewModel {
        val uiState: StateFlow<UIState>
        val sideEffect: Channel<SideEffect>
        fun onEventDispatcher(uiIntent: UIIntent)
    }

    interface Directions {
        suspend fun navigateToBack()
        suspend fun navigateToHome()
    }
}