package uz.gita.latizx.tbcmobile.screen.auth.verify

import uz.gita.latizx.comman.model.RecipientData
import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.tbcmobile.screen.auth.intro.IntroScreen
import uz.gita.latizx.tbcmobile.screen.auth.pin_code.PinCodeScreen
import uz.gita.latizx.presenter.auth.verify.VerifyContract
import uz.gita.latizx.tbcmobile.screen.main.home.HomeScreen
import uz.gita.latizx.tbcmobile.screen.succes.SuccessScreen
import javax.inject.Inject

class VerifyDirections @Inject constructor(
    private val appNavigator: AppNavigator,
) : VerifyContract.Directions {
    override suspend fun navigateToBack() {
        appNavigator.back()
    }

    override suspend fun navigateToPinCode() {
        appNavigator.navigateTo(PinCodeScreen())
    }

    override suspend fun navigateToSuccess(recipientData: RecipientData) {
        appNavigator.replaceAll(SuccessScreen(recipientData = recipientData))
    }

    override suspend fun closeScreen() {
        appNavigator.replaceAll(IntroScreen())
    }

    override suspend fun closeTransferVerifyScreen() {
        appNavigator.replaceAll(HomeScreen())
    }
}