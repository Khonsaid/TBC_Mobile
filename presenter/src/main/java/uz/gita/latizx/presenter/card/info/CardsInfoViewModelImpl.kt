package uz.gita.latizx.presenter.card.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.usecase.card.GetCardsUseCase
import javax.inject.Inject
import kotlin.onSuccess

@HiltViewModel
class CardsInfoViewModelImpl @Inject constructor(
    private val directions: CardsInfoContract.Directions,
    private val getCardsUseCase: GetCardsUseCase,
) : CardsInfoContract.CardsInfoViewModel, ViewModel() {
    override val uiState = MutableStateFlow<CardsInfoContract.UIState>(CardsInfoContract.UIState())
    override val sideEffect = Channel<CardsInfoContract.SideEffect>()
    val _sideEffect = sideEffect.receiveAsFlow()

    init {
        getCards()
    }

    override fun onEventDispatcher(uiIntent: CardsInfoContract.UIIntent) {
        when (uiIntent) {
            CardsInfoContract.UIIntent.OpenAddCardScreen -> viewModelScope.launch { directions.navigateToAddCard() }
            CardsInfoContract.UIIntent.OpenPrevScreen -> viewModelScope.launch { directions.navigateToBack() }
            CardsInfoContract.UIIntent.ShowEditCardName -> {

            }
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

    private inline fun reduce(block: (CardsInfoContract.UIState) -> CardsInfoContract.UIState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }
}