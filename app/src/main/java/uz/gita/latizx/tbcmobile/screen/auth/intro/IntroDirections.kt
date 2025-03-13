package uz.gita.latizx.hwl64_module.screen.auth.intro

import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.tbcmobile.screen.auth.sign_in.SignInScreen
import uz.gita.latizx.tbcmobile.screen.auth.sign_up.SignUpScreen
import uz.gita.latizx.tbcmobile.screen.support.support.SupportScreen
import uz.gita.latizx.presenter.auth.intro.IntroContract

import javax.inject.Inject

class IntroDirections @Inject constructor(
    private val appNavigator: AppNavigator,
) : IntroContract.Directions {
    override suspend fun navigateToSignIn() {
        appNavigator.navigateTo(SignInScreen())
    }

    override suspend fun navigateToSignUp() {
        appNavigator.navigateTo(SignUpScreen())
    }

    override suspend fun navigateToSupport() {
        appNavigator.navigateTo(SupportScreen())
    }
}