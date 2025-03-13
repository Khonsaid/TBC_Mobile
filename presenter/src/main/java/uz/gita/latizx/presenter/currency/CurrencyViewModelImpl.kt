package uz.gita.latizx.presenter.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.usecase.exchange_rate.ExchangeRateUseCase
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModelImpl @Inject constructor(
    private val directions: CurrencyContract.Directions,
    private val exchangeRateUseCase: ExchangeRateUseCase,
) : CurrencyContract.CurrencyViewModel, ViewModel() {
    override val uiState = MutableStateFlow<CurrencyContract.UIState>(CurrencyContract.UIState())
    override val sideEffect = Channel<CurrencyContract.SideEffect>()
    val _sideEffect = sideEffect.receiveAsFlow()

    init {
        viewModelScope.launch { sideEffect.send(CurrencyContract.SideEffect(showLoading = true)) }
        getExchangeRate()
    }

    override fun onEventDispatcher(uiIntent: CurrencyContract.UIIntent) {
        when (uiIntent) {
            is CurrencyContract.UIIntent.OpenPrev -> viewModelScope.launch { directions.navigateToPrev() }
            is CurrencyContract.UIIntent.ChangeScreen -> reduce { it.copy(isCurrency = !it.isCurrency) }
            is CurrencyContract.UIIntent.ChangeCalculate -> reduce { it.copy(fromUZS = !it.fromUZS) }
            is CurrencyContract.UIIntent.InputSum -> {
                if (uiState.value.fromUZS)
                    reduce { it.copy(valueUZS = uiIntent.sum) }
                else
                    reduce { it.copy(valueUSD = uiIntent.sum) }

                if (uiIntent.sum.isEmpty()) {
                    reduce { it.copy(valueUSD = "", valueUZS = "") }
                    return
                }
                calculate(uiIntent.sum)
            }
        }
    }

    private fun calculate(sum: String) {
        val sanitizedSum = sum.replace(",", ".")
        val rateTBC = uiState.value.exchangeRates[0].rate.toDouble().toInt() + ((uiState.value.exchangeRates[0].rate.toDouble() / 100) * 0.4)
        if (!uiState.value.fromUZS) {
            val result = rateTBC * sanitizedSum.toDouble()
            reduce { it.copy(valueUZS = String.format(Locale.getDefault(), "%.2f", result)) }
        } else {
            val result = sanitizedSum.toDouble() / rateTBC
            reduce { it.copy(valueUSD = String.format(Locale.getDefault(), "%.2f", result)) }
        }
    }

    private fun getExchangeRate() {
        exchangeRateUseCase.invoke().onEach { result ->
            result.onSuccess { data ->
                reduce { it.copy(exchangeRates = data, calculateRate = data[0]) }
                initData()
            }
            result.onFailure {
            }
        }.launchIn(viewModelScope)
    }

    private inline fun reduce(block: (CurrencyContract.UIState) -> CurrencyContract.UIState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }

    private fun initData() {
        val rateUSD = uiState.value.exchangeRates[0].rate.toDouble().toInt() + ((uiState.value.exchangeRates[0].rate.toDouble() / 100) * 0.4)
        val rateUZS = (1 / uiState.value.exchangeRates[0].rate.toDouble()) * 1000
        reduce {
            it.copy(
                rateUSD = rateUSD.toString(),
                rateUZS = rateUZS.toString()
            )
        }
        viewModelScope.launch { sideEffect.send(CurrencyContract.SideEffect(showLoading = false)) }
    }
}

