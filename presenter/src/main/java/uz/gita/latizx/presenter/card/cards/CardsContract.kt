package uz.gita.latizx.presenter.card.cards

import kotlinx.coroutines.flow.StateFlow

interface CardsContract {

    sealed interface UIIntent {
        data object OpenAddCardScreen : UIIntent
        data object OpenPrevScreen : UIIntent
    }

    data object UIState

    interface CardsViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(uiIntent: UIIntent)
    }

    interface Directions {
        suspend fun navigateToBack()
        suspend fun navigateToAddCard()
    }
}