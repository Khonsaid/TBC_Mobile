package uz.gita.latizx.presenter.home

import kotlinx.coroutines.flow.StateFlow
import uz.gita.latizx.comman.model.CardsData
import uz.gita.latizx.comman.model.HomeItemVertical

interface HomeContract {

    sealed interface UiIntent {
        data object OpenHomeTransactions : UiIntent
        data object BalanceDisplayed : UiIntent
        data object OpenHomePayments : UiIntent
        data object OpenRecipient : UiIntent
        data object OpenHomeCards : UiIntent
        data object OpenHomeCardsInfo : UiIntent
    }

    data class UiState(
        val balance: String = "0",
        val homeItems: List<HomeItemVertical> = emptyList(),
        val cards: List<CardsData> = emptyList(),
        val isLoading: Boolean = true,
        val isBalanceDisplayed: Boolean = false,
    )

    interface HomeViewModel {
        val uiState: StateFlow<UiState>
        fun onEventDispatcher(uiIntent: UiIntent)
    }

    interface Directions {
        suspend fun navigateToHomeTransaction()
        suspend fun navigateToHomePayment()
        suspend fun navigateToHomeRecipient()
        suspend fun navigateToCardsInfo()
        suspend fun navigateToCards()
    }
}