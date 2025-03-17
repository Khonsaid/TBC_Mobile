package uz.gita.latizx.presenter.card.info

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import uz.gita.latizx.comman.model.CardsData

interface CardsInfoContract {

    sealed interface UIIntent {
        data object OpenAddCardScreen : UIIntent
        data object OpenPrevScreen : UIIntent
        data class ShowEditCardName(val index: Int) : UIIntent
        data class UpdateCardName(val name: String) : UIIntent
        data object DismissEditCardName : UIIntent

        object HideTextDialog : UIIntent

        data class ShowDeleteCard(val id: Int) : UIIntent
        data object DismissDeleteCardDialog : UIIntent
        data object DeleteCard : UIIntent
    }

    data class UIState(
        val balance: String = "0",
        val cards: List<CardsData> = emptyList(),
        val showLoading: Boolean = false,
    )

    data class SideEffect(
        val showDeleteCardDialog: Boolean = false,
        val showEditCardName: Boolean = false,
        val showMessageDialog: Boolean = false,
        val message: Int = 0,
        val cardName: String = "",
    )

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