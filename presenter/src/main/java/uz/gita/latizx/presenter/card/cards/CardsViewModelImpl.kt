package uz.gita.latizx.presenter.card.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardsViewModelImpl @Inject constructor(
    private val directions: CardsContract.Directions,
) : CardsContract.CardsViewModel, ViewModel() {
    override val uiState = MutableStateFlow<CardsContract.UIState>(CardsContract.UIState)

    override fun onEventDispatcher(uiIntent: CardsContract.UIIntent) {
        when (uiIntent) {
            CardsContract.UIIntent.OpenAddCardScreen -> viewModelScope.launch { directions.navigateToAddCard() }
            CardsContract.UIIntent.OpenPrevScreen -> viewModelScope.launch { directions.navigateToBack() }
        }
    }


}