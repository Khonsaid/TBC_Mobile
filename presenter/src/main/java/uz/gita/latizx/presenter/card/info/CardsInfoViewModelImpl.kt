package uz.gita.latizx.presenter.card.info

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.presenter.card.info.CardsInfoContract.SideEffect
import uz.gita.latizx.usecase.card.DeleteCardByIdUseCase
import uz.gita.latizx.usecase.card.GetCardsUseCase
import uz.gita.latizx.usecase.card.UpdateCardUseCase
import javax.inject.Inject
import kotlin.onSuccess

@HiltViewModel
class CardsInfoViewModelImpl @Inject constructor(
    private val directions: CardsInfoContract.Directions,
    private val getCardsUseCase: GetCardsUseCase,
    private val deleteCardByIdUseCase: DeleteCardByIdUseCase,
    private val updateCardUseCase: UpdateCardUseCase,
) : CardsInfoContract.CardsInfoViewModel, ViewModel() {
    override val uiState = MutableStateFlow<CardsInfoContract.UIState>(CardsInfoContract.UIState())
    override val sideEffect = Channel<CardsInfoContract.SideEffect>()
    val _sideEffect = sideEffect.receiveAsFlow()
    var cardId = -1
    var indexCard = -1

    init {
        getCards()
    }

    override fun onEventDispatcher(uiIntent: CardsInfoContract.UIIntent) {
        when (uiIntent) {
            CardsInfoContract.UIIntent.OpenAddCardScreen -> viewModelScope.launch { directions.navigateToAddCard() }
            CardsInfoContract.UIIntent.OpenPrevScreen -> viewModelScope.launch { directions.navigateToBack() }
            CardsInfoContract.UIIntent.HideTextDialog -> viewModelScope.launch { sideEffect.send(SideEffect(showMessageDialog = false)) }

            CardsInfoContract.UIIntent.DeleteCard -> {
                viewModelScope.launch { sideEffect.send(SideEffect(showDeleteCardDialog = false)) }
                if (cardId == -1) return
                deleteCardByIdUseCase.invoke(id = cardId).onEach { result ->
                    reduce { it.copy(showLoading = true) }
                    result.onSuccess {
                        reduce { it.copy(showLoading = false) }
                        cardId = -1
                        viewModelScope.launch { sideEffect.send(SideEffect(message = 1, showMessageDialog = true)) }
                        getCards()
                    }
                    result.onFailure {
                        reduce { it.copy(showLoading = false) }
                        Log.d("TTT", "deleteCardByIdUseCase onFailure: $it")
                        viewModelScope.launch { sideEffect.send(SideEffect(message = 2, showMessageDialog = true)) }
                    }
                }.launchIn(viewModelScope)
            }

            CardsInfoContract.UIIntent.DismissDeleteCardDialog -> {
                viewModelScope.launch { sideEffect.send(SideEffect(showDeleteCardDialog = false)) }
                cardId = -1
            }

            is CardsInfoContract.UIIntent.ShowDeleteCard -> {
                cardId = uiIntent.id
                viewModelScope.launch { sideEffect.send(SideEffect(showDeleteCardDialog = true)) }
            }

            is CardsInfoContract.UIIntent.ShowEditCardName -> {
                indexCard = uiIntent.index
                viewModelScope.launch { sideEffect.send(SideEffect(showEditCardName = true, cardName = uiState.value.cards[indexCard].name)) }
            }

            CardsInfoContract.UIIntent.DismissEditCardName -> {
                viewModelScope.launch { sideEffect.send(SideEffect(showDeleteCardDialog = false)) }
                indexCard = -1
            }

            is CardsInfoContract.UIIntent.UpdateCardName -> {
                viewModelScope.launch { sideEffect.send(SideEffect(showEditCardName = false)) }
                if (indexCard == -1) return
                val card = uiState.value.cards[indexCard]
                val name = uiIntent.name.replace(" ", "")
                updateCardUseCase.invoke(id = card.id, name = name, themeType = card.themeType, isVisible = card.isVisible.toString()).onEach { result ->
                    reduce { it.copy(showLoading = true) }
                    result.onSuccess {
                        reduce { it.copy(showLoading = false) }
                        indexCard = -1
                        viewModelScope.launch { sideEffect.send(SideEffect(message = 3, showMessageDialog = true)) }
                        getCards()
                    }
                    result.onFailure {
                        reduce { it.copy(showLoading = false) }
                        viewModelScope.launch { sideEffect.send(SideEffect(message = 2, showMessageDialog = true)) }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun getCards() {
        getCardsUseCase.invoke().onEach { result ->
            reduce { it.copy(showLoading = true) }
            result.onSuccess { data ->
                reduce { it.copy(showLoading = false) }
                reduce { it.copy(cards = data) }
            }
            result.onFailure {
                reduce { it.copy(showLoading = false) }

            }
        }.launchIn(viewModelScope)
    }

    private inline fun reduce(block: (CardsInfoContract.UIState) -> CardsInfoContract.UIState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }
}