package uz.gita.latizx.tbcmobile.screen.transaction

import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.presenter.transaction.TransactionContract
import javax.inject.Inject

class TransactionsDirections @Inject constructor(
    private val appNavigator: AppNavigator,
) : TransactionContract.Directions {
    override suspend fun navigateToBack() {
        appNavigator.back()
    }

}