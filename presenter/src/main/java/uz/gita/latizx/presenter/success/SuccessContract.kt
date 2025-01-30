package uz.gita.latizx.presenter.success

import kotlinx.coroutines.flow.StateFlow

interface SuccessContract {

    sealed interface SuccessIntent {
        data object OpenMoneyTransfersScreen : SuccessIntent
    }

    data class UIState(
        val recipientPan: String = "",
        val recipientName: String = "",
    )

    interface SuccessViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: SuccessIntent)
    }

    interface Directions {
        suspend fun navigateToMoneyTransfers()
    }
}