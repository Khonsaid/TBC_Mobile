package uz.gita.latizx.tbcmobile.screen.history.transaction

import uz.gita.latizx.comman.model.HistoryItemsData
import uz.gita.latizx.presenter.history.transaction.TransactionContract
import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.tbcmobile.screen.history.transaction_detail.TransactionDetailScreen
import javax.inject.Inject

class TransactionsDirections @Inject constructor(
    private val appNavigator: AppNavigator,
) : TransactionContract.Directions {
    override suspend fun navigateToBack() {
        appNavigator.back()
    }

    override suspend fun navigateToTransactionDetailScreen(historyData: HistoryItemsData) {
        appNavigator.navigateTo(TransactionDetailScreen(historyData = historyData))
    }
}