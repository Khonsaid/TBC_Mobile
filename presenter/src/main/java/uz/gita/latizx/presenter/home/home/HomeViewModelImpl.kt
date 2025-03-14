package uz.gita.latizx.presenter.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.latizx.comman.R
import uz.gita.latizx.comman.enums.HomeItemVerticalEnum
import uz.gita.latizx.comman.model.CardsData
import uz.gita.latizx.comman.model.HomeItemVertical
import uz.gita.latizx.presenter.utils.ResourceManager
import uz.gita.latizx.usecase.card.GetCardsUseCase
import uz.gita.latizx.usecase.exchange_rate.ExchangeRateUseCase
import uz.gita.latizx.usecase.home.TotalBalanceUseCase
import uz.gita.latizx.usecase.settings.SettingsUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val directions: HomeContract.Directions,
    private val getTotalBalanceUseCase: TotalBalanceUseCase,
    private val getCardsUseCase: GetCardsUseCase,
    private val exchangeRateUseCase: ExchangeRateUseCase,
    private val settingsUseCase: SettingsUseCase,
    private val resourceManager: ResourceManager,
) : HomeContract.HomeViewModel, ViewModel() {
    override val uiState = MutableStateFlow<HomeContract.UiState>(HomeContract.UiState(homeItems = homeItems()))

    init {
        initData()
    }

    private fun initData() {
        getTotalSum()
        getCards()
        getExchangeRate()
        reduce { it.copy(isBalanceDisplayed = settingsUseCase.isBalanceDisplayed()) }
    }

    override fun onEventDispatcher(uiIntent: HomeContract.UiIntent) {
        when (uiIntent) {
            HomeContract.UiIntent.OpenHomeTransactions -> viewModelScope.launch { directions.navigateToHomeTransaction() }
            HomeContract.UiIntent.OpenHomePayments -> viewModelScope.launch { directions.navigateToHomePayment() }
            HomeContract.UiIntent.OpenRecipient -> viewModelScope.launch { directions.navigateToHomeRecipient() }
            HomeContract.UiIntent.OpenHomeCards -> viewModelScope.launch { directions.navigateToCards() }
            HomeContract.UiIntent.OpenHomeCardsInfo -> viewModelScope.launch { directions.navigateToCardsInfo() }
            HomeContract.UiIntent.OpenCurrency -> viewModelScope.launch { directions.navigateToCurrency() }
            HomeContract.UiIntent.OpenSupport -> viewModelScope.launch { directions.navigateToSupport() }
            HomeContract.UiIntent.OpenSettings -> viewModelScope.launch { directions.navigateToSettings() }
            HomeContract.UiIntent.BalanceDisplayed -> reduce { uiState.value.copy(isBalanceDisplayed = settingsUseCase.changeBalanceDisplayed()) }

            HomeContract.UiIntent.RefreshData -> viewModelScope.launch {
                reduce { it.copy(isRefreshing = true) }
                delay(1000)
                reduce { it.copy(balance = "12000000") }
                initData()
                reduce { it.copy(isRefreshing = false) }
            }
        }
    }

    private fun getTotalSum() {
        reduce { it.copy(isLoading = true) }
        getTotalBalanceUseCase.invoke().onEach { result ->
            result.onSuccess { data ->
                reduce { it.copy(isLoading = false, balance = data.totalBalance.toString()) }
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

    private fun getExchangeRate() {
        exchangeRateUseCase.invoke().onEach { result ->
            result.onSuccess { data ->
                reduce { it.copy(exchangeRateModel = data[0]) }
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

    private fun cards(): List<CardsData> = listOf(
        CardsData(
            id = 1,
            name = "Visa Classic",
            amount = 500000,
            expiredMonth = 12,
            expiredYear = 2026,
            pan = "3456",
            owner = "John Doe",
            themeType = 1,
            isVisible = true
        ),
        CardsData(
            id = 2,
            name = "MasterCard Gold",
            amount = 1250000,
            expiredMonth = 8,
            expiredYear = 2025,
            pan = "7654",
            owner = "Alice Smith",
            themeType = 2,
            isVisible = true
        ),
        CardsData(
            id = 3,
            name = "UzCard",
            amount = 300000,
            expiredMonth = 5,
            expiredYear = 2027,
            pan = "9012",
            owner = "Bob Johnson",
            themeType = 3,
            isVisible = false
        )
    )
}