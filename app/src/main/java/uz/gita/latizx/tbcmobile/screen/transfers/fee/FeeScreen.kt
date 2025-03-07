package uz.gita.latizx.tbcmobile.screen.transfers.fee

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.latizx.comman.formatWithSeparator
import uz.gita.latizx.comman.model.GetFeeData
import uz.gita.latizx.comman.model.RecipientData
import uz.gita.latizx.presenter.transfer.fee.FeeContract
import uz.gita.latizx.presenter.transfer.fee.FeeViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.components.animation.LoadingDialog
import uz.gita.latizx.tbcmobile.ui.components.button.AppFilledButton
import uz.gita.latizx.tbcmobile.ui.components.text.SuperScriptText
import uz.gita.latizx.tbcmobile.ui.components.textfield.TransferCardField
import uz.gita.latizx.tbcmobile.ui.components.topbar.AppTopBar
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

class FeeScreen(private val recipientData: RecipientData, private val getFeeData: GetFeeData, private val senderId: String) : Screen {
    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val viewModel = getViewModel<FeeViewModelImpl, FeeViewModelImpl.Factory>(
            viewModelFactory = {
                it.onCreate(recipientData = recipientData, getFeeData = getFeeData, senderId = senderId)
            }
        )
        val uiState = viewModel.uiState.collectAsState()
        FeeScreenContent(uiState = uiState, eventDispatcher = viewModel::onEventDispatcher)

        if (uiState.value.showLoading) LoadingDialog()
    }
}

@Composable
private fun FeeScreenContent(
    uiState: State<FeeContract.UIState> = remember { mutableStateOf(FeeContract.UIState()) },
    eventDispatcher: (FeeContract.UIIntent) -> Unit = {},
) {
    Scaffold(
        topBar = {
            AppTopBar(
                text = R.string.transfer,
                hasPrevBtn = true,
                onClickBack = { eventDispatcher(FeeContract.UIIntent.OpenMoneyScreen) },
                onClickPrev = { eventDispatcher(FeeContract.UIIntent.OpenTransferScreen) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(12.dp)
        ) {
            Spacer(modifier = Modifier.padding(top = 36.dp))
            TransferCardField(
                name = uiState.value.nameRecipient,
                cardData = uiState.value.panRecipient,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.5f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SuperScriptText(
                    normalText = uiState.value.sum.formatWithSeparator(),
                    superText = "UZS",
                    color = MaterialTheme.colorScheme.error,
                    normalFonSize = MaterialTheme.typography.displayLarge.fontSize,
                    superTextFontSize = MaterialTheme.typography.displaySmall.fontSize,
                )
                Text(
                    text = stringResource(
                        R.string.transfer_commission,
                        uiState.value.fee.formatWithSeparator()
                    ) + stringResource(R.string.rates_my_space_currency_symbol),
                    color = MaterialTheme.colorScheme.onTertiary,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Normal
                )
            }

            AppFilledButton(
                modifier = Modifier
                    .padding(vertical = 12.dp),
                text = stringResource(R.string.transfer_transfer),
                color = AppTheme.colorScheme.backgroundBrandTertiary,
                colorText = AppTheme.colorScheme.borderContrastOnWhite,
                onClick = {
                    eventDispatcher(FeeContract.UIIntent.ClickConfirmation)
                }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    FeeScreenContent()
}