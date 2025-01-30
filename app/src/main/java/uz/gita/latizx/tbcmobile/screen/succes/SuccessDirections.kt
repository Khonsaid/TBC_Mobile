package uz.gita.latizx.tbcmobile.screen.succes

import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.tbcmobile.screen.main.home.HomeScreen
import uz.gita.latizx.presenter.success.SuccessContract
import javax.inject.Inject

class SuccessDirections @Inject constructor(
    private val appNavigator: AppNavigator,
) : SuccessContract.Directions {
    override suspend fun navigateToMoneyTransfers() {
        appNavigator.replaceAll(HomeScreen())
    }
}