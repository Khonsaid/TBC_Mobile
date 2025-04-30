package uz.gita.latizx.tbcmobile.screen.settings.map

import uz.gita.latizx.presenter.settings.map.MapContract
import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import javax.inject.Inject

class MapDirectionsImpl @Inject constructor(
    private val appNavigator: AppNavigator,
) : MapContract.Directions {
    override suspend fun navigateToBack() {
        appNavigator.back()
    }
}