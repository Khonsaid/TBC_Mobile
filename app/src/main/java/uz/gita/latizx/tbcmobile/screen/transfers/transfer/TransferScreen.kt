package uz.gita.latizx.tbcmobile.screen.transfers.transfer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import uz.gita.latizx.comman.formatWithSeparator
import uz.gita.latizx.comman.model.RecipientData
import uz.gita.latizx.presenter.transfer.transfer.TransferContract
import uz.gita.latizx.presenter.transfer.transfer.TransferViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.screen.components.animation.LoadingDialog
import uz.gita.latizx.tbcmobile.screen.components.button.AppFilledButton
import uz.gita.latizx.tbcmobile.screen.components.button.CircleNumberButton
import uz.gita.latizx.tbcmobile.screen.components.dialog.TextDialog
import uz.gita.latizx.tbcmobile.screen.components.text.SuperScriptText
import uz.gita.latizx.tbcmobile.screen.components.textfield.TransferCardField
import uz.gita.latizx.tbcmobile.screen.components.topbar.AppTopBar
import uz.gita.latizx.tbcmobile.screen.main.bottom_sheet.ChooseCardBottomSheet

data class TransferScreen(private val recipientPan: String, private val recipientName: String) : Screen {
    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val viewModel = getViewModel<TransferViewModelImpl, TransferViewModelImpl.Factory>(
            viewModelFactory = {
                it.onCreate(RecipientData(recipientPan = recipientPan, recipientName = recipientName))
            }
        )
        val uiState = viewModel.uiState.collectAsState()

        BottomSheetNavigator(
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            TransferScreenContent(uiState = uiState, eventDispatcher = viewModel::onEventDispatcher)
        }
        var showDialog by remember { mutableStateOf(false) }
        var dialogMessage by remember { mutableIntStateOf(0) }

        LaunchedEffect(Unit) {
            viewModel._sideEffect.collect {
                dialogMessage = it.message
                showDialog = true
            }
        }
        if (showDialog) {
            TextDialog(
                text = dialogMessage,
            )
        }
        if (uiState.value.showLoading) {
            LoadingDialog()
        }
    }
}

@Composable
private fun TransferScreenContent(
    uiState: State<TransferContract.UIState> = remember { mutableStateOf(TransferContract.UIState()) },
    eventDispatcher: (TransferContract.UIIntent) -> Unit = {},
) {
    Scaffold(
        topBar = {
            AppTopBar(
                text = R.string.transfer,
                hasPrevBtn = true,
                onClickPrev = {
                    eventDispatcher(TransferContract.UIIntent.OpenMoneyScreen)
                },
                onClickBack = {
                    eventDispatcher(TransferContract.UIIntent.OpenRecipientScreen)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            TransferCardField(
                name = uiState.value.name,
                cardData = uiState.value.balance,
                hasArrowDown = true,
                onClick = {
                    eventDispatcher(TransferContract.UIIntent.ShowSelectCardBottomSheet)
                }
            )
            TransferCardField(
                name = uiState.value.nameRecipient,
                cardData = uiState.value.panRecipient,
            )
            SuperScriptText(
                normalText = uiState.value.sum.formatWithSeparator(),
                superText = "UZS",
                normalFonSize = MaterialTheme.typography.displayMedium.fontSize,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.5f),
            )

            BoxNumbers(modifier = Modifier.weight(3f),
                onClick = { eventDispatcher(TransferContract.UIIntent.TransferSum(it)) },
                onClickRemove = { eventDispatcher(TransferContract.UIIntent.ClickRemove) }
            )
            AppFilledButton(
                modifier = Modifier
                    .padding(vertical = 12.dp),
                text = stringResource(R.string.components_next),
                onClick = {
                    eventDispatcher(TransferContract.UIIntent.OpenFeeScreen)
                }
            )

            val bottomSheetNavigator = LocalBottomSheetNavigator.current

            if (uiState.value.isBottomSheetVisible) {
                val dialog = ChooseCardBottomSheet(uiState.value.cardList)
                dialog.onSelectCard = { cardIndex ->
                    eventDispatcher(TransferContract.UIIntent.SelectCard(cardIndex = cardIndex))
                    bottomSheetNavigator.hide()
                }
                dialog.onDismissRequest = {
                    bottomSheetNavigator.hide()
                    eventDispatcher(TransferContract.UIIntent.HideSelectCardBottomSheet)
                }
                bottomSheetNavigator.show(dialog)
            }
            if (bottomSheetNavigator.isVisible) eventDispatcher(TransferContract.UIIntent.HideSelectCardBottomSheet)
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TransferScreenContent()
}

@Composable
private fun BoxNumbers(
    modifier: Modifier,
    onClick: (String) -> Unit,
    onClickRemove: () -> Unit,
) {
    val numbers = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf(".", "0", "<")
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        numbers.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                row.forEach { text ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f) // Tugmalarni kvadrat qilib qiladi
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (text == "<") {
                            IconButton(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                onClick = { onClickRemove() }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_chevron_left_24_regular),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onTertiary
                                )
                            }
                        } else {
                            CircleNumberButton(
                                modifier = Modifier.fillMaxSize(),
                                text = text,
                                color = MaterialTheme.colorScheme.onTertiary,
                                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                                onClick = { onClick(text) }
                            )
                        }
                    }
                }
            }
        }
    }
}