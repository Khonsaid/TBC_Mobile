package uz.gita.latizx.presenter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.latizx.comman.formatWithSeparator
import uz.gita.latizx.comman.model.HomeItemVertical
import uz.gita.latizx.comman.enums.HomeItemVerticalEnum
import uz.gita.latizx.usecase.card.GetCardsUseCase
import uz.gita.latizx.usecase.home.TotalBalanceUseCase
import javax.inject.Inject
import uz.gita.latizx.comman.R
@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val directions: HomeContract.Directions,
    private val getTotalBalanceUseCase: TotalBalanceUseCase,
    private val getCardsUseCase: GetCardsUseCase,
) : HomeContract.HomeViewModel, ViewModel() {
    override val uiState = MutableStateFlow<HomeContract.UiState>(HomeContract.UiState(homeItems = homeItems()))

    init {
        getTotalSum()
        getCards()
    }

    override fun onEventDispatcher(uiIntent: HomeContract.UiIntent) {
        when (uiIntent) {
            is HomeContract.UiIntent.OpenHomeTransactions -> viewModelScope.launch { directions.navigateToHomeTransaction() }
            is HomeContract.UiIntent.OpenHomePayments -> viewModelScope.launch { directions.navigateToHomePayment() }
            is HomeContract.UiIntent.OpenRecipient -> viewModelScope.launch { directions.navigateToHomeRecipient() }
            is HomeContract.UiIntent.OpenHomeCards -> viewModelScope.launch { directions.navigateToCards() }
            is HomeContract.UiIntent.OpenHomeCardsInfo -> viewModelScope.launch { directions.navigateToCardsInfo() }
            is HomeContract.UiIntent.BalanceDisplayed -> {
                reduce { uiState.value.copy(isBalanceDisplayed = !uiState.value.isBalanceDisplayed) }
            }
        }
    }

    private fun getTotalSum() {
        reduce { it.copy(isLoading = true) }
        getTotalBalanceUseCase.invoke().onEach { result ->
            result.onSuccess { data ->
                reduce { it.copy(isLoading = false) }
                reduce { it.copy(balance = data.totalBalance.toString().formatWithSeparator()) }
            }
            result.onFailure {
                reduce { it.copy(isLoading = false) }
            }
        }.launchIn(viewModelScope)
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

    private inline fun reduce(block: (HomeContract.UiState) -> HomeContract.UiState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }

    private fun homeItems() = listOf(
        HomeItemVertical(
            R.string.cards_management_digital_card_title,
            R.string.signing_sign_up_the_last_name_empty,
            R.drawable.ill_credit_card_lg,
            R.color.pfm_gradient_end_color,
            HomeItemVerticalEnum.CARDS
        ),
        HomeItemVertical(
            R.string.cards_management_digital_card_title,
            R.string.signing_sign_up_the_last_name_empty,
            R.drawable.ill_credit_card_lg,
            R.color.palette_yellow_30,
            HomeItemVerticalEnum.CREDITS
        ),
        HomeItemVertical(
            R.string.cards_management_digital_card_title,
            R.string.signing_sign_up_the_last_name_empty,
            R.drawable.ill_credit_card_lg,
            R.color.palette_green_10,
            HomeItemVerticalEnum.CREDIT_CARDS
        )
    )
}