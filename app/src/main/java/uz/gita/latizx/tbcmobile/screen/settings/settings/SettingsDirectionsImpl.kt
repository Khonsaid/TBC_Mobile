package uz.gita.latizx.tbcmobile.screen.settings.settings

import uz.gita.latizx.presenter.settings.settings.SettingsContract
import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.tbcmobile.screen.auth.intro.IntroScreen
import uz.gita.latizx.tbcmobile.screen.settings.general.GeneralSettingsScreen
import javax.inject.Inject

class SettingsDirectionsImpl @Inject constructor(
    private val appNavigator: AppNavigator,
) : SettingsContract.Directions {

    override suspend fun navigateToPrev() {
        appNavigator.back()
    }

    override suspend fun navigateToIntro() {
        appNavigator.replaceAll(IntroScreen())
    }

    override suspend fun navigateToGeneralSettingsScreenIntro() {
        appNavigator.navigateTo(GeneralSettingsScreen())
    }
}