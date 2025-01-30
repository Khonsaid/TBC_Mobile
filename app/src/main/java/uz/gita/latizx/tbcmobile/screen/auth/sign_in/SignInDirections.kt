package uz.gita.latizx.tbcmobile.screen.auth.sign_in

import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.presenter.auth.sign_in.SignInContract
import uz.gita.latizx.comman.enums.VerifyEnum
import uz.gita.latizx.comman.model.VerifyData
import uz.gita.latizx.tbcmobile.screen.auth.verify.VerifyScreen
import javax.inject.Inject

class SignInDirections @Inject constructor(
    private val appNavigator: AppNavigator,
) : SignInContract.Directions {
    override suspend fun navigateToVerify(phoneNumber: String, password: String) {
        appNavigator.navigateTo(VerifyScreen(verifyEnum = VerifyEnum.SigIn, VerifyData(password = password, phone = phoneNumber), recipientData = null))
    }

    override suspend fun navigateToBAck() {
        appNavigator.back()
    }
}