package uz.gita.latizx.tbcmobile.screen.transfers.recipient

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.latizx.presenter.transfer.recipient.RecipientContract
import uz.gita.latizx.presenter.transfer.recipient.RecipientViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.components.animation.LoadingDialog
import uz.gita.latizx.tbcmobile.ui.components.button.AppFilledButton
import uz.gita.latizx.tbcmobile.ui.components.dialog.TextDialog
import uz.gita.latizx.tbcmobile.ui.components.textfield.CardInputField
import uz.gita.latizx.tbcmobile.ui.components.textfield.InputField
import uz.gita.latizx.tbcmobile.ui.components.topbar.AppTopBar
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

class RecipientScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<RecipientViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState()
        RecipientScreenContent(uiState = uiState, eventDispatcher = viewModel::onEventDispatcher)

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
                onDismiss = { showDialog = false }
            )
        }
        if (uiState.value.showLoading) LoadingDialog()
    }
}

@Composable
private fun RecipientScreenContent(
    uiState: State<RecipientContract.UIState> = remember { mutableStateOf(RecipientContract.UIState()) },
    eventDispatcher: (RecipientContract.UIIntent) -> Unit = {},
) {
    Scaffold(
        containerColor = AppTheme.colorScheme.backgroundPrimary,
        topBar = {
            AppTopBar(
                text = R.string.transfer,
                hasPrevBtn = true,
                onClickPrev = {
                    eventDispatcher(RecipientContract.UIIntent.OpenMoneyScreen)
                },
                onClickBack = {
                    eventDispatcher(RecipientContract.UIIntent.OpenMoneyScreen)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CardInputField(
                text = R.string.cards_add_enter_card_number,
                onValueChange = { eventDispatcher(RecipientContract.UIIntent.CheckCardNumber(it)) }
//                onSubmit = { eventDispatcher(RecipientContract.UIIntent.CheckCardNumber(it)) }
            )

            if (uiState.value.showRecipient.isNotEmpty()) {
                InputField(
                    label = stringResource(R.string.transfer_recipient_full_name),
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier,
                    value = uiState.value.showRecipient,
                    hint = "",
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            AppFilledButton(
                modifier = Modifier.padding(bottom = 12.dp),
                text = stringResource(R.string.components_next),
                color = AppTheme.colorScheme.backgroundBrandTertiary,
                onClick = {
                    eventDispatcher(RecipientContract.UIIntent.OpenTransferScreen)
                }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    RecipientScreenContent()
}