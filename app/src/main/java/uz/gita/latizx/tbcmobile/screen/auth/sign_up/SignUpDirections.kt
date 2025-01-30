package uz.gita.latizx.tbcmobile.screen.auth.sign_up

import uz.gita.latizx.comman.enums.VerifyEnum
import uz.gita.latizx.comman.model.VerifyData
import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.tbcmobile.screen.auth.verify.VerifyScreen
import uz.gita.latizx.presenter.auth.sign_up.SignUpContract
import javax.inject.Inject

class SignUpDirections @Inject constructor(
    private val appNavigator: AppNavigator,
) : SignUpContract.Directions {
    override suspend fun navigateToVerify(phoneNumber: String, password: String) {
        appNavigator.navigateTo(VerifyScreen(verifyEnum = VerifyEnum.SignUp, VerifyData(phone = phoneNumber, password = password), recipientData = null))
    }

    override suspend fun navigateToBack() {
        appNavigator.back()
    }
}