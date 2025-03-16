package uz.gita.latizx.tbcmobile.screen.settings.security

import uz.gita.latizx.presenter.settings.security.SecurityContract
import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.tbcmobile.screen.auth.sign_in.SignInScreen
import javax.inject.Inject

class SecurityDirectionsImpl @Inject constructor(
    private val appNavigator: AppNavigator,
) : SecurityContract.Directions {
    override suspend fun navigateToBack() {
        appNavigator.back()
    }

    override suspend fun navigateToChangePassword() {
        appNavigator.navigateTo(SignInScreen())
    }
}