package uz.gita.latizx.tbcmobile.screen.main.currency

import uz.gita.latizx.presenter.currency.CurrencyContract
import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import javax.inject.Inject

class CurrencyDirectionsImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : CurrencyContract.Directions{
    override suspend fun navigateToPrev() {
        appNavigator.back()
    }
}