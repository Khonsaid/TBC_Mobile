package uz.gita.latizx.presenter.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.usecase.transfer.GetHistoryUseCase
import javax.inject.Inject

@HiltViewModel
class TransactionViewModelImpl @Inject constructor(
    private val directions: TransactionContract.Directions,
    private val getHistoryUseCase: GetHistoryUseCase,
) : TransactionContract.TransactionsViewModel, ViewModel() {
    override val uiState = MutableStateFlow<TransactionContract.UIState>(TransactionContract.UIState())
    override val sideEffect = Channel<TransactionContract.SideEffect>()
    val _sideEffect = sideEffect.receiveAsFlow()

    init {
        viewModelScope.launch {
            val pagingData = getHistoryUseCase.invoke()
                .cachedIn(viewModelScope)
            reduce { it.copy(transactions = pagingData) }
        }
    }

    override fun onEventDispatcher(uiIntent: TransactionContract.UIIntent) {
        when (uiIntent) {
            TransactionContract.UIIntent.OpenPrevScreen -> viewModelScope.launch { directions.navigateToBack() }
        }
    }

    private inline fun reduce(block: (TransactionContract.UIState) -> TransactionContract.UIState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }
}