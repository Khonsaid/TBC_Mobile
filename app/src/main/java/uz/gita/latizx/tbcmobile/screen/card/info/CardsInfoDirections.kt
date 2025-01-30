package uz.gita.latizx.tbcmobile.screen.card.info

import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.tbcmobile.screen.card.add.AddCardScreen
import uz.gita.latizx.presenter.card.info.CardsInfoContract
import javax.inject.Inject

class CardsInfoDirections @Inject constructor(
    private val appNavigator: AppNavigator,
) : CardsInfoContract.Directions {
    override suspend fun navigateToBack() {
        appNavigator.back()
    }

    override suspend fun navigateToAddCard() {
        appNavigator.navigateTo(AddCardScreen())
    }
}