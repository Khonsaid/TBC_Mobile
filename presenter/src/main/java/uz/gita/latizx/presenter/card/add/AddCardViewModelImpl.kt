package uz.gita.latizx.presenter.card.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.usecase.card.AddCardUseCase
import javax.inject.Inject

//import uz.gita.latizx.common.R
@HiltViewModel
class AddCardViewModelImpl @Inject constructor(
    private val directions: AddCardContract.Directions,
    private val addCardUseCase: AddCardUseCase,
) : AddCardContract.AddCardViewModel, ViewModel() {
    override val uiState = MutableStateFlow<AddCardContract.UIState>(AddCardContract.UIState())
    override val sideEffect = Channel<AddCardContract.SideEffect>()
    val _sideEffect = sideEffect.receiveAsFlow()

    override fun onEventDispatcher(uiIntent: AddCardContract.UIIntent) {
        when (uiIntent) {
            is AddCardContract.UIIntent.AddCard -> {
                if (checkInputData(uiIntent)) {
                    reduce { it.copy(showLoading = true) }
                    addCardUseCase.invoke(
                        pan = uiIntent.pan,
                        expiredYear = uiIntent.expiredYear,
                        expiredMonth = uiIntent.expiredMonth,
                        name = uiIntent.name
                    ).onEach { result ->
                        result.onSuccess {
                            reduce { it.copy(showLoading = false) }
                            viewModelScope.launch { directions.navigateToHome() }
                        }
                        result.onFailure {
                            reduce { it.copy(showLoading = false) }
                            viewModelScope.launch {
                                sideEffect.send(AddCardContract.SideEffect(showDialog = true, message = 1))
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }

            is AddCardContract.UIIntent.ClickExpiredYear -> reduce { it.copy(listData = yearList, isBottomSheetVisible = true) }
            is AddCardContract.UIIntent.ClickExpiredMonth -> reduce { it.copy(listData = monthList, isBottomSheetVisible = true) }
            is AddCardContract.UIIntent.HideBottomSheet -> reduce { it.copy(isBottomSheetVisible = false) }
            is AddCardContract.UIIntent.OpenPrevScreen -> viewModelScope.launch { directions.navigateToBack() }
            is AddCardContract.UIIntent.CloseDialog -> viewModelScope.launch { sideEffect.send(AddCardContract.SideEffect(showDialog = false)) }
        }
    }

    private fun checkInputData(card: AddCardContract.UIIntent.AddCard): Boolean {
        return if (card.pan.isEmpty() || card.pan.length != 16) {
            viewModelScope.launch {
                sideEffect.send(AddCardContract.SideEffect(showDialog = true, message = 2))
            }
            false
        } else if (card.expiredMonth.isEmpty() || card.expiredYear.isEmpty()) {
            viewModelScope.launch {
                sideEffect.send(AddCardContract.SideEffect(showDialog = true, message = 3))
            }
            false
        } else {
            true
        }
    }

    private fun reduce(block: (AddCardContract.UIState) -> AddCardContract.UIState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }

    private val yearList = listOf(
        "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035",
    )
    private val monthList = listOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
}