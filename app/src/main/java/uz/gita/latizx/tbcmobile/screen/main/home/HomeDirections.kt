package uz.gita.latizx.tbcmobile.screen.main.home

import uz.gita.latizx.presenter.home.home.HomeContract
import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.tbcmobile.screen.card.cards.CardsScreen
import uz.gita.latizx.tbcmobile.screen.card.info.CardsInfoScreen
import uz.gita.latizx.tbcmobile.screen.history.transaction.TransactionScreen
import uz.gita.latizx.tbcmobile.screen.main.currency.CurrencyScreen
import uz.gita.latizx.tbcmobile.screen.main.payment.PaymentsScreen
import uz.gita.latizx.tbcmobile.screen.settings.settings.SettingsScreen
import uz.gita.latizx.tbcmobile.screen.support.support.SupportScreen
import uz.gita.latizx.tbcmobile.screen.transfers.recipient.RecipientScreen
import javax.inject.Inject

class HomeDirections @Inject constructor(
    private val appNavigator: AppNavigator,
) : HomeContract.Directions {

    override suspend fun navigateToHomeTransaction() {
        appNavigator.navigateTo(TransactionScreen())
    }

    override suspend fun navigateToHomePayment() {
        appNavigator.navigateTo(PaymentsScreen())
    }

    override suspend fun navigateToHomeRecipient() {
        appNavigator.navigateTo(RecipientScreen())
    }

    override suspend fun navigateToCardsInfo() {
        appNavigator.navigateTo(CardsInfoScreen())
    }

    override suspend fun navigateToCards() {
        appNavigator.navigateTo(CardsScreen())
    }

    override suspend fun navigateToSupport() {
        appNavigator.navigateTo(SupportScreen())
    }

    override suspend fun navigateToCurrency() {
        appNavigator.navigateTo(CurrencyScreen())
    }

    override suspend fun navigateToSettings() {
        appNavigator.navigateTo(SettingsScreen())
    }
}