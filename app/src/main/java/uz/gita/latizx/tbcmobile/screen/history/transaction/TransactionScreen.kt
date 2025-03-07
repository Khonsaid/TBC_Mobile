package uz.gita.latizx.tbcmobile.screen.history.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.latizx.comman.formatWithSeparator
import uz.gita.latizx.presenter.history.transaction.TransactionContract
import uz.gita.latizx.presenter.history.transaction.TransactionViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.components.items.ItemHistoryTransaction
import uz.gita.latizx.tbcmobile.ui.components.search.CustomSearch
import uz.gita.latizx.tbcmobile.ui.components.topbar.AppTopBar
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme
import uz.gita.latizx.tbcmobile.utils.formatToHour

class TransactionScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<TransactionViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState()
        TransactionScreenContent(uiState = uiState, eventDispatcher = viewModel::onEventDispatcher)

    }
}

@Composable
private fun TransactionScreenContent(
    uiState: State<TransactionContract.UIState> = remember { mutableStateOf(TransactionContract.UIState()) },
    eventDispatcher: (TransactionContract.UIIntent) -> Unit = {},
) {
    val data = uiState.value.transactions.collectAsLazyPagingItems()

    Scaffold(
        containerColor = AppTheme.colorScheme.backgroundPrimary,
        topBar = {
            AppTopBar(
                text = R.string.transfers,
                onClickBack = { eventDispatcher(TransactionContract.UIIntent.OpenPrevScreen) }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(0.8f)) { CustomSearch(hint = stringResource(R.string.search_menu_title)) {} }
                    Row(modifier = Modifier.weight(0.2f), horizontalArrangement = Arrangement.SpaceAround) {
                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(R.drawable.ic_document_download_24_regular),
                                contentDescription = "close btn",
                                tint = AppTheme.colorScheme.iconAccentCyan
                            )
                        }
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_document_download_24_regular),
                                contentDescription = "close btn",
                                tint = AppTheme.colorScheme.iconAccentCyan
                            )
                        }
                    }
                }
            }
        }) {
        Column(modifier = Modifier.padding(it)) {
            if (data.itemCount == 0) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                    ) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 10.dp, bottom = 10.dp), // 10 dp oraliq
                            painter = painterResource(R.drawable.ic_arrow_left_down_24_regular),
                            contentDescription = null
                        )
                        // Top Icon
                        Icon(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(start = 10.dp, top = 10.dp),
                            painter = painterResource(R.drawable.ic_arrow_right_top_24_regular),
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.empty_transactions_label),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onTertiary,
                            fontWeight = FontWeight.Normal,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        )
                    )
                }
            }
            LazyColumn {
                items(data.itemCount) { post ->
                    val data = data[post]
                    data?.let {
                        ItemHistoryTransaction(
                            name = if (it.from == "9860080808090010") stringResource(R.string.transfer_card_between_own_cards)
                            else it.to.uppercase(),
                            date = it.time.formatToHour(),
                            transferSum = if (it.type == "income") "+${it.amount.toString().formatWithSeparator()}"
                            else "-${it.amount.toString().formatWithSeparator()}",
                            onTap = {
                                eventDispatcher.invoke(TransactionContract.UIIntent.OpenTransactionDetailScreen(it))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TransactionScreenContent()
}