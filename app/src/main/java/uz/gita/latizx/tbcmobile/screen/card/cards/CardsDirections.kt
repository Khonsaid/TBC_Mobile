package uz.gita.latizx.tbcmobile.screen.card.cards

import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.tbcmobile.screen.card.add.AddCardScreen
import uz.gita.latizx.presenter.card.cards.CardsContract
import javax.inject.Inject

class CardsDirections @Inject constructor(
    private val appNavigator: AppNavigator,
) : CardsContract.Directions {
    override suspend fun navigateToBack() {
        appNavigator.back()
    }

    override suspend fun navigateToAddCard() {
        appNavigator.navigateTo(AddCardScreen())
    }
}