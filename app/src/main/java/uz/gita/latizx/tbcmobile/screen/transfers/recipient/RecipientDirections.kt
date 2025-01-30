package uz.gita.latizx.tbcmobile.screen.transfers.recipient

import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.tbcmobile.screen.main.home.HomeScreen
import uz.gita.latizx.presenter.transfer.recipient.RecipientContract
import uz.gita.latizx.tbcmobile.screen.transfers.transfer.TransferScreen
import javax.inject.Inject

class RecipientDirections @Inject constructor(
    private val appNavigator: AppNavigator,
) : RecipientContract.Directions {
    override suspend fun navigateToTransfer(recipientCardNumber: String, recipientName: String) {
        appNavigator.navigateTo(TransferScreen(recipientPan = recipientCardNumber, recipientName = recipientName))
    }

    override suspend fun navigateToMoney() {
        appNavigator.navigateTo(HomeScreen())
    }
}