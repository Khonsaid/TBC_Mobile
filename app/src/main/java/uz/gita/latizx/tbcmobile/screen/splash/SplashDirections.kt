package uz.gita.latizx.tbcmobile.screen.splash

import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.tbcmobile.screen.auth.intro.IntroScreen
import uz.gita.latizx.tbcmobile.screen.auth.pin_code.PinCodeScreen
import uz.gita.latizx.presenter.splash.SplashContract
import javax.inject.Inject

class SplashDirections @Inject constructor(
    private val appNavigator: AppNavigator,
) : SplashContract.Directions {
    override suspend fun navigateToIntro() {
        appNavigator.replace(IntroScreen())
    }

    override suspend fun navigateToPinCode() {
        appNavigator.replace(PinCodeScreen(setPinCode = false))
    }
}