package uz.gita.latizx.tbcmobile.screen.auth.pin_code

import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.tbcmobile.screen.auth.intro.IntroScreen
import uz.gita.latizx.tbcmobile.screen.auth.sign_in.SignInScreen
import uz.gita.latizx.tbcmobile.screen.main.home.HomeScreen
import uz.gita.latizx.presenter.auth.pin_code.PinCodeContract
import javax.inject.Inject

class PinCodeDirections @Inject constructor(
    private val appNavigator: AppNavigator,
) : PinCodeContract.Directions {
    override suspend fun navigateToHome() {
        appNavigator.replaceAll(HomeScreen())
    }

    override suspend fun navigateToInto() {
        appNavigator.navigateTo(IntroScreen())
    }

    override suspend fun navigateToSignIn() {
        appNavigator.navigateTo(SignInScreen())
    }

}