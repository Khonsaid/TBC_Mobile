package uz.gita.latizx.presenter.card.info

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import uz.gita.latizx.comman.model.CardsData

interface CardsInfoContract {

    sealed interface UIIntent {
        data object OpenAddCardScreen : UIIntent
        data object OpenPrevScreen : UIIntent
        data object ShowEditCardName : UIIntent
    }

    data class UIState(
        val balance: String = "0",
        val cards: List<CardsData> = emptyList(),
    )

    data class SideEffect(val message: Int)

    interface CardsInfoViewModel {
        val uiState: StateFlow<UIState>
        val sideEffect: Channel<SideEffect>
        fun onEventDispatcher(uiIntent: UIIntent)
    }

    interface Directions {
        suspend fun navigateToBack()
        suspend fun navigateToAddCard()
    }
}