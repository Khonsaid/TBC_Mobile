package uz.gita.latizx.presenter.history.transaction_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.comman.model.HistoryItemsData
import uz.gita.latizx.presenter.history.transaction_detail.TransactionDetailContract.TransactionDetailViewModel

@HiltViewModel(assistedFactory = TransactionDetailViewModelImpl.Factory::class)
class TransactionDetailViewModelImpl @AssistedInject constructor(
    @Assisted private val historyData: HistoryItemsData,
    private val directions: TransactionDetailContract.Directions,
) : TransactionDetailViewModel, ViewModel() {
    override val uiState = MutableStateFlow<TransactionDetailContract.UIState>(TransactionDetailContract.UIState())
    override val sideEffect = Channel<TransactionDetailContract.SideEffect>()
    val _sideEffect = sideEffect.receiveAsFlow()

    @AssistedFactory
    interface Factory {
        fun onCreate(historyData: HistoryItemsData): TransactionDetailViewModelImpl
    }

    init {
        reduce { it.copy(historyData = historyData) }
    }

    override fun onEventDispatcher(uiIntent: TransactionDetailContract.UIIntent) {
        when (uiIntent) {
            is TransactionDetailContract.UIIntent.OpenPrevScreen -> viewModelScope.launch { directions.navigateToBack() }
            is TransactionDetailContract.UIIntent.OpenHomeScreen -> viewModelScope.launch { directions.navigateToHome() }
        }
    }

    private inline fun reduce(block: (TransactionDetailContract.UIState) -> TransactionDetailContract.UIState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }
}