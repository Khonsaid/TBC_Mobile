package uz.gita.latizx.tbcmobile.screen.transfers.money

import kotlinx.coroutines.flow.StateFlow

interface MoneyTransfersContract {
    data object UIState


    sealed interface UIIntent {
        data object OpenPrevScreen : UIIntent
    }

    interface TransfersViewModel {
        val uiSate: StateFlow<UIState>
        fun onEventDispatcher(uiIntent: UIIntent)
    }

    interface Directions {
        suspend fun navigateToBack()
    }
}