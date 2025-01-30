package uz.gita.latizx.tbcmobile.screen.transfers.fee

import uz.gita.latizx.comman.enums.VerifyEnum
import uz.gita.latizx.comman.model.RecipientData
import uz.gita.latizx.comman.model.TransferVerifyData
import uz.gita.latizx.comman.model.VerifyData
import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.tbcmobile.screen.auth.verify.VerifyScreen
import uz.gita.latizx.tbcmobile.screen.main.home.HomeScreen
import uz.gita.latizx.presenter.transfer.fee.FeeContract
import javax.inject.Inject

class FeeDirections @Inject constructor(
    private val appNavigator: AppNavigator,
) : FeeContract.Directions {
    override suspend fun navigateToTransfer() {
        appNavigator.back()
    }

    override suspend fun navigateToVerify(transferVerifyData: TransferVerifyData, recipientData: RecipientData) {
        appNavigator.navigateTo(
            VerifyScreen(
                VerifyEnum.Transfer,
                VerifyData(phone = "", password = null),
                transferVerifyData = transferVerifyData,
                recipientData = recipientData
            )
        )
    }

    override suspend fun navigateToMoney() {
        appNavigator.replace(HomeScreen())
    }
}