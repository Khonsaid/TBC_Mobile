package uz.gita.latizx.tbcmobile.screen.transfers.money

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen

class MoneyTransfersScreen : Screen {
    @Composable
    override fun Content() {
//        val viewModel = getViewModel<MoneyTransfersViewModelImpl>()
//        val uiState = viewModel.uiSate.asStateFlow()

        TransfersScreenContent()

    }
}

@Composable
private fun TransfersScreenContent(
    uiState: State<MoneyTransfersContract.UIState> = remember { mutableStateOf(MoneyTransfersContract.UIState) },
    eventDispatcher: (MoneyTransfersContract.UIIntent) -> Unit = {},
) {

}