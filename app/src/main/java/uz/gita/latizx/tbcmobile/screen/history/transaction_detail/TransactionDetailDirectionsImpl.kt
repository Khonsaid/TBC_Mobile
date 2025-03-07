package uz.gita.latizx.tbcmobile.screen.history.transaction_detail

import uz.gita.latizx.presenter.history.transaction_detail.TransactionDetailContract
import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.tbcmobile.screen.main.home.HomeScreen
import javax.inject.Inject

class TransactionDetailDirectionsImpl @Inject constructor(
    private val appNavigator: AppNavigator,
) : TransactionDetailContract.Directions {
    override suspend fun navigateToBack() {
        appNavigator.back()
    }

    override suspend fun navigateToHome() {
        appNavigator.replaceAll(HomeScreen())
    }
}