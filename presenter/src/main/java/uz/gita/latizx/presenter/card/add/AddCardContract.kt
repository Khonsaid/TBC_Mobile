package uz.gita.latizx.presenter.card.add

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow

interface AddCardContract {

    sealed interface UIIntent {
        data object OpenPrevScreen : UIIntent
        data object ClickExpiredMonth : UIIntent
        data object ClickExpiredYear : UIIntent
        data object HideBottomSheet : UIIntent
        data class AddCard(
            val pan: String,
            val expiredYear: String,
            val expiredMonth: String,
            val name: String,
        ) : UIIntent
    }

    data class UIState(
        val listData: List<String> = emptyList(),
        val isBottomSheetVisible: Boolean = false,
        val showLoading: Boolean = false,
        val message: Int = 0,
    )

    data class SideEffect(val message: Int)

    interface AddCardViewModel {
        val uiState: StateFlow<UIState>
        val sideEffect: Channel<SideEffect>
        fun onEventDispatcher(uiIntent: UIIntent)
    }

    interface Directions {
        suspend fun navigateToBack()
        suspend fun navigateToHome()
    }
}