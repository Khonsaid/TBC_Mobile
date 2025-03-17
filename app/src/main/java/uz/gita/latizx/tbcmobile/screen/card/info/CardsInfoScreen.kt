package uz.gita.latizx.tbcmobile.screen.card.info

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.latizx.comman.formatWithSeparator
import uz.gita.latizx.presenter.card.info.CardsInfoContract
import uz.gita.latizx.presenter.card.info.CardsInfoViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.components.animation.LoadingDialog
import uz.gita.latizx.tbcmobile.ui.components.button.CircleImageButton
import uz.gita.latizx.tbcmobile.ui.components.dialog.ConfirmationDialog
import uz.gita.latizx.tbcmobile.ui.components.dialog.EditCardNameDialog
import uz.gita.latizx.tbcmobile.ui.components.dialog.TextDialog
import uz.gita.latizx.tbcmobile.ui.components.topbar.AppTopBar
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme
import kotlin.math.absoluteValue

class CardsInfoScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<CardsInfoViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState()
        CardsInfoScreenContent(uiState = uiState, eventDispatcher = viewModel::onEventDispatcher)

        var showDialog by remember { mutableStateOf(false) }
        var showEditCardName by remember { mutableStateOf(false) }
        var editedCardName by remember { mutableStateOf("") }
        var dialogMessage by remember { mutableIntStateOf(0) }
        var showDeleteCardDialog by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            viewModel._sideEffect.collect {
                showDeleteCardDialog = it.showDeleteCardDialog
                dialogMessage = it.message
                showDialog = it.showMessageDialog
                showEditCardName = it.showEditCardName
                editedCardName = it.cardName
            }
        }
        val errorMessage = when (dialogMessage) {
            1 -> stringResource(R.string.cards_state_card_has_been_deleted)

            2 -> stringResource(R.string.components_server_error)
            3 -> stringResource(R.string.transfer_template_name_successfully_changed)
            else -> ""
        }

        if (showEditCardName) {
            EditCardNameDialog(
                text = R.string.cards_edit_card_message,
                textYesButton = R.string.trusted_devices_untrusted_dialog_keep,
                textNoButton = R.string.cancel,
                onClickYes = { viewModel.onEventDispatcher(CardsInfoContract.UIIntent.UpdateCardName(it)) },
                onDismissRequest = { viewModel.onEventDispatcher(CardsInfoContract.UIIntent.DismissEditCardName) },
                value = editedCardName
            )
        }

        if (showDialog) {
            TextDialog(
                text = errorMessage,
                onDismiss = {
                    viewModel.onEventDispatcher(CardsInfoContract.UIIntent.HideTextDialog)
                }
            )
        }
        if (showDeleteCardDialog) {
            ConfirmationDialog(
                text = R.string.transfer_do_you_want_to_delete_template,
                textYesButton = R.string.login_yes,
                textNoButton = R.string.login_no,
                onClickYes = { viewModel.onEventDispatcher(CardsInfoContract.UIIntent.DeleteCard) },
                onDismissRequest = { viewModel.onEventDispatcher(CardsInfoContract.UIIntent.DismissDeleteCardDialog) }
            )
        }
        if (uiState.value.showLoading) {
            LoadingDialog()
        }
    }
}

@Composable
private fun CardsInfoScreenContent(
    uiState: State<CardsInfoContract.UIState> = remember { mutableStateOf(CardsInfoContract.UIState()) },
    eventDispatcher: (CardsInfoContract.UIIntent) -> Unit = {},
) {
    Scaffold(
        containerColor = AppTheme.colorScheme.backgroundPrimary,
        topBar = {
            AppTopBar(
                text = R.string.cards_cards,
                onClickBack = { eventDispatcher(CardsInfoContract.UIIntent.OpenPrevScreen) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {

            val pages = uiState.value.cards
            val pagerState = rememberPagerState(pageCount = { pages.size })

            HorizontalPager(
                modifier = Modifier.fillMaxWidth(),
                state = pagerState,
                verticalAlignment = Alignment.CenterVertically
            ) { index ->
                PageContent(
                    modifier = Modifier.graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - index) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                        alpha = lerp(
                            start = 0.4f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        scaleX = lerp(
                            start = 0.8f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        scaleY = scaleX
                    },
                    cardName = uiState.value.cards[index].name,
                    sum = uiState.value.cards[index].amount.toString().formatWithSeparator(),
                    balanceText = R.string.cards_balance_label,
                    cardLastNumbers = uiState.value.cards[index].pan,
                    index = index,
                    cardColor = Brush.linearGradient(
                        colors = listOf(colorResource(R.color.palette_cool_gray_50), colorResource(R.color.palette_cool_gray_10)),
                        start = Offset(0f, 0f),
                        end = Offset(1000f, 1000f)
                    ),
                )
            }

            // Indicators Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) AppTheme.colorScheme.backgroundStatusInfoSecondary
                        else AppTheme.colorScheme.backgroundStatusInfo
                    Box(
                        modifier = Modifier
                            .padding(6.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(10.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CircleImageButton(
                    img = R.drawable.ic_plus_24_regular,
                    text = R.string.cards_menu_top_up,
                    onClick = {}
                )
                CircleImageButton(
                    img = R.drawable.ic_pencil_edit_24_regular,
                    text = R.string.cards_menu_edit,
                    onClick = {
                        eventDispatcher(CardsInfoContract.UIIntent.ShowEditCardName(pagerState.currentPage))
                    }
                )
                CircleImageButton(
                    img = R.drawable.ic_delete,
                    text = R.string.cards_menu_delete,
                    onClick = {
                        eventDispatcher(CardsInfoContract.UIIntent.ShowDeleteCard(uiState.value.cards[pagerState.currentPage].id))
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CardsInfoScreenContent()
}

@Composable
private fun PageContent(
    cardName: String,
    sum: String,
    @StringRes
    balanceText: Int,
    cardColor: Brush,
    modifier: Modifier,
    cardLastNumbers: String,
    index: Int,
    onClick: () -> Unit = {},
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 16.dp, horizontal = 12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f)
                        .background(color = colorResource(R.color.white))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = cardName,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "$sum UZS",
                            color = colorResource(R.color.palette_green_70),
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = stringResource(balanceText),
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1f)
                        .background(brush = cardColor)
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "****$cardLastNumbers",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Image(
                        painter = painterResource(if (index % 2 == 0) uz.gita.latizx.comman.R.drawable.ag_ps_humo else uz.gita.latizx.comman.R.drawable.ag_ps_uzpay),
                        contentDescription = null
                    )
                }
            }
        }
    }
}