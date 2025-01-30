package uz.gita.latizx.tbcmobile.screen.card.add

import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.presenter.card.add.AddCardContract
import uz.gita.latizx.tbcmobile.screen.main.home.HomeScreen
import javax.inject.Inject

class AddCardDirections @Inject constructor(
    private val appNavigator: AppNavigator,
) : AddCardContract.Directions {
    override suspend fun navigateToBack() {
        appNavigator.back()
    }

    override suspend fun navigateToHome() {
        appNavigator.replaceAll(HomeScreen())
    }
}