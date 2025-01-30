package uz.gita.latizx.tbcmobile.screen.transfers.transfer

import uz.gita.latizx.comman.model.GetFeeData
import uz.gita.latizx.comman.model.RecipientData
import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.tbcmobile.screen.main.home.HomeScreen
import uz.gita.latizx.tbcmobile.screen.transfers.fee.FeeScreen
import uz.gita.latizx.presenter.transfer.transfer.TransferContract
import javax.inject.Inject

class TransferDirections @Inject constructor(
    private val appNavigator: AppNavigator,
) : TransferContract.Directions {
    override suspend fun navigateToRecipient() {
        appNavigator.back()
    }

    override suspend fun navigateToFee(recipientData: RecipientData, getFeeData: GetFeeData, senderId: String) {
        appNavigator.navigateTo(FeeScreen(recipientData = recipientData, getFeeData = getFeeData, senderId = senderId))
    }

    override suspend fun navigateToMoney() {
        appNavigator.replace(HomeScreen())
    }
}