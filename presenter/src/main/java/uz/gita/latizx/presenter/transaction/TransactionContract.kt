package uz.gita.latizx.presenter.transaction

import androidx.paging.PagingData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import uz.gita.latizx.comman.model.HistoryItems

interface TransactionContract {
    sealed interface UIIntent {
        data object OpenPrevScreen : UIIntent
    }

    data class SideEffect(val message: Int)
    data class UIState(
        val transactions: Flow<PagingData<HistoryItems>> = flow { },
    )

    interface TransactionsViewModel {
        val uiState: StateFlow<UIState>
        val sideEffect: Channel<SideEffect>
        fun onEventDispatcher(uiIntent: UIIntent)
    }

    interface Directions {
        suspend fun navigateToBack()
    }
}