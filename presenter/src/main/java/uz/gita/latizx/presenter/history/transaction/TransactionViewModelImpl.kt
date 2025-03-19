package uz.gita.latizx.presenter.history.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.usecase.card.GetCardsUseCase
import uz.gita.latizx.usecase.transfer.GetHistoryUseCase
import javax.inject.Inject

@HiltViewModel
class TransactionViewModelImpl @Inject constructor(
    private val directions: TransactionContract.Directions,
    private val getCardsUseCase: GetCardsUseCase,
    private val getHistoryUseCase: GetHistoryUseCase,
) : TransactionContract.TransactionsViewModel, ViewModel() {
    override val uiState = MutableStateFlow<TransactionContract.UIState>(TransactionContract.UIState())
    override val sideEffect = Channel<TransactionContract.SideEffect>()
    val _sideEffect = sideEffect.receiveAsFlow()

    init {
        getCards()
        viewModelScope.launch {
            val pagingData = getHistoryUseCase.invoke()
                .cachedIn(viewModelScope)
            reduce { it.copy(transactions = pagingData) }
        }
    }

    override fun onEventDispatcher(uiIntent: TransactionContract.UIIntent) {
        when (uiIntent) {
            TransactionContract.UIIntent.OpenPrevScreen -> viewModelScope.launch { directions.navigateToBack() }
            is TransactionContract.UIIntent.OpenTransactionDetailScreen -> viewModelScope.launch { directions.navigateToTransactionDetailScreen(uiIntent.historyData) }
        }
    }

    private fun getCards() {
        getCardsUseCase.invoke().onEach { result ->
            result.onSuccess { data ->
                reduce { it.copy(cards = data) }
            }
            result.onFailure {

            }
        }.launchIn(viewModelScope)
    }

    private inline fun reduce(block: (TransactionContract.UIState) -> TransactionContract.UIState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }
}