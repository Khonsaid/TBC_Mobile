package uz.gita.latizx.tbcmobile.screen.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.latizx.tbcmobile.utils.getSectionHeight
import uz.gita.latizx.tbcmobile.utils.parallaxLayoutModifier
import uz.gita.latizx.presenter.home.HomeContract
import uz.gita.latizx.presenter.home.HomeViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.screen.components.button.IconWithNotificationBadge
import uz.gita.latizx.tbcmobile.screen.components.button.SPHomeButton
import uz.gita.latizx.tbcmobile.screen.components.items.ItemCardInfo
import uz.gita.latizx.tbcmobile.screen.components.items.ItemHomeVertical
import uz.gita.latizx.tbcmobile.screen.components.other.DotBox

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<HomeViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState()
        HomeScreenContent(uiState, viewModel::onEventDispatcher)

        val scope = rememberCoroutineScope()
        val showDialog by remember { mutableStateOf(false) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    uiState: State<HomeContract.UiState> = remember { mutableStateOf(HomeContract.UiState()) },
    eventDispatcher: (HomeContract.UiIntent) -> Unit = { },
) {
    val sectionHeight: Dp = LocalConfiguration.current.getSectionHeight()
    val scrollState = rememberScrollState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        sheetContent = {
            BottomSheetContent(
                uiState = uiState,
                eventDispatcher = eventDispatcher
            )
        },
        sheetContainerColor = Color(0xFFf6faff),
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = sectionHeight / 2 //BottomSheetDefaults.SheetPeekHeight
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .height(sectionHeight * 0.5f)
                .padding(16.dp)
                .parallaxLayoutModifier(scrollState, 2),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Birinchi qator
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconWithNotificationBadge(
                    iconRes = R.drawable.ic_home_user,
                    badgeContent = "1"
                )

                Text(
                    text = stringResource(R.string.home_welcome_text),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Medium
                )
            }
            // Ikkinchi qator
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.home_balance_total_title),
                    color = MaterialTheme.colorScheme.onTertiary,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (uiState.value.isBalanceDisplayed) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(
                                    text = uiState.value.balance,
                                    color = MaterialTheme.colorScheme.onTertiary,
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = stringResource(R.string.rates_my_space_currency_symbol),
                                    color = MaterialTheme.colorScheme.onTertiary,
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        } else {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                repeat(5) { DotBox() }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 12.dp),
                    ) {
                        IconButton(
                            onClick = { eventDispatcher(HomeContract.UiIntent.BalanceDisplayed) }
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (uiState.value.isBalanceDisplayed) R.drawable.ic_eye_off
                                    else R.drawable.ic_eye
                                ),
                                contentDescription = "ic eye"
                            )
                        }
                    }
                }
            }
            // Uchinchi qator
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                SPHomeButton(
                    modifier = Modifier.weight(1f),
                    text = R.string.transaction_transactions,
                    image = R.drawable.ic_clock_backward_24_regular,
                    onClick = { eventDispatcher(HomeContract.UiIntent.OpenHomeTransactions) }
                )
                SPHomeButton(
                    modifier = Modifier.weight(1f),
                    text = R.string.home_menu_transfers,
                    image = R.drawable.ic_arrow_swap_horizontal_24_regular,
                    onClick = { eventDispatcher(HomeContract.UiIntent.OpenRecipient) }
                )
                SPHomeButton(
                    modifier = Modifier.weight(1f),
                    text = R.string.home_payment,
                    image = R.drawable.ic_home_sp_payment,
                    onClick = { eventDispatcher(HomeContract.UiIntent.OpenHomePayments) }
                )
            }
        }
    }
}

@Composable
private fun BottomSheetContent(
    uiState: State<HomeContract.UiState>,
    eventDispatcher: (HomeContract.UiIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ItemHomeVertical(
            title = uiState.value.homeItems[0].title,
            text = uiState.value.homeItems[0].text,
            image = uiState.value.homeItems[0].image,
            cardColor = uiState.value.homeItems[0].cardColor,
            onClick = {}
        )
        if (uiState.value.cards.isEmpty()) {
            ItemHomeVertical(
                title = uiState.value.homeItems[1].title,
                text = uiState.value.homeItems[1].text,
                image = uiState.value.homeItems[1].image,
                cardColor = uiState.value.homeItems[1].cardColor,
                onClick = { eventDispatcher(HomeContract.UiIntent.OpenHomeCards) }
            )
        } else {
            ItemCardInfo(
                cards = uiState.value.cards,
//                cardName = uiState.value.cards[index].name,
//                sum = uiState.value.cards[index].amount.toString(),
                balanceText = R.string.cards_balance_label,
//                pan = uiState.value.cards[index].pan,
                onClickCard = { eventDispatcher(HomeContract.UiIntent.OpenHomeCardsInfo) },
                onClickAddCard = { eventDispatcher(HomeContract.UiIntent.OpenHomeCards) }
            )
        }
        ItemHomeVertical(
            title = uiState.value.homeItems[2].title,
            text = uiState.value.homeItems[2].text,
            image = uiState.value.homeItems[2].image,
            cardColor = uiState.value.homeItems[2].cardColor,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun Preview() {
    HomeScreenContent()
}