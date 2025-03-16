package uz.gita.latizx.tbcmobile.screen.settings.general

import uz.gita.latizx.presenter.settings.general.GeneralSettingsContract
import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import javax.inject.Inject

class GeneralSettingsDirectionsImpl @Inject constructor(
    private val appNavigator: AppNavigator,
) : GeneralSettingsContract.Directions {
    override suspend fun navigateToBack() {
        appNavigator.back()
    }
}