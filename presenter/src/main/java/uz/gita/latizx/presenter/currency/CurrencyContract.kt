package uz.gita.latizx.presenter.currency

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import uz.gita.latizx.comman.model.ExchangeRateModel

interface CurrencyContract {
    sealed interface UIIntent {
        data object OpenPrev : UIIntent
        data object ChangeScreen : UIIntent
        data object ChangeCalculate : UIIntent
        data class InputSum(val sum: String) : UIIntent
    }

    data class UIState(
        val exchangeRates: List<ExchangeRateModel> = emptyList(),
        val isCurrency: Boolean = true,
        val fromUZS: Boolean = true,
        val rateUSD: String = "",
        val rateUZS: String = "",
        val valueUSD: String = "",
        val valueUZS: String = "",
        val calculateRate: ExchangeRateModel? = null,
    )

    interface CurrencyViewModel {
        val uiState: StateFlow<UIState>
        val sideEffect: Channel<SideEffect>
        fun onEventDispatcher(uiIntent: UIIntent)
    }

    interface Directions {
        suspend fun navigateToPrev()
    }

    data class SideEffect(val showLoading: Boolean = false)
}