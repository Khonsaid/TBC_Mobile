package uz.gita.latizx.tbcmobile.screen.auth.verify

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import kotlinx.coroutines.launch
import uz.gita.latizx.comman.enums.VerifyEnum
import uz.gita.latizx.comman.model.RecipientData
import uz.gita.latizx.comman.model.TransferVerifyData
import uz.gita.latizx.comman.model.VerifyData
import uz.gita.latizx.presenter.auth.verify.VerifyContract
import uz.gita.latizx.presenter.auth.verify.VerifyViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.components.button.NextButton
import uz.gita.latizx.tbcmobile.ui.components.dialog.CustomDialog
import uz.gita.latizx.tbcmobile.ui.components.textfield.OtpField
import uz.gita.latizx.tbcmobile.ui.components.topbar.AppTopBar
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

class VerifyScreen(
    private val verifyEnum: VerifyEnum,
    private val data: VerifyData? = null,
    private val transferVerifyData: TransferVerifyData? = null,
    private val recipientData: RecipientData?,
) : Screen {
    @OptIn(ExperimentalVoyagerApi::class)
    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    override fun Content() {
        val viewModel = getViewModel<VerifyViewModelImpl, VerifyViewModelImpl.Factory>(
            viewModelFactory = {
                it.onCreate(verifyEnum = verifyEnum, data = data, transferVerifyData = transferVerifyData, recipientData = recipientData)
            }
        )
        val uiState = viewModel.uiState.collectAsState()
        VerifyContent(uiState, viewModel::onEventDispatcher, data?.phone ?: "")

        val scope = rememberCoroutineScope()
        var showDialog by remember { mutableStateOf(false) }
        var dialogMessage by remember { mutableIntStateOf(0) }
        scope.launch {
            viewModel._sideEffect.collect {
                dialogMessage = it.message
                showDialog = true
            }
        }
        if (showDialog) {
            CustomDialog(
                text = dialogMessage,
                image = R.drawable.fingerprint_dialog_error,
                textButton = R.string.signing_close,
                onDismissRequest = { showDialog = false }
            )
        }
    }
}

@Composable
private fun VerifyContent(
    uiState: State<VerifyContract.UiState> = remember { mutableStateOf(VerifyContract.UiState()) },
    eventDispatcher: (VerifyContract.UiIntent) -> Unit = {},
    phoneNumber: String,
) {
    var code by remember { mutableStateOf("") }

    LaunchedEffect(uiState.value.timeStarted) {
        if (!uiState.value.timeStarted) {
            code = "" // Clear the OTP field
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppTheme.colorScheme.backgroundPrimary
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppTopBar(
                text = R.string.signing_registration_title_message,
                onClickBack = {
                    eventDispatcher(VerifyContract.UiIntent.CloseScreen)
                },
                hasPrevBtn = true,
                onClickPrev = {
                    eventDispatcher(VerifyContract.UiIntent.OpenPrevScreen)
                }
            )
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = stringResource(R.string.auth_enter_sms_code),
                color = AppTheme.colorScheme.textTertiary,
                style = AppTheme.typography.bodyMedium,
            )
            Text(
                text = phoneNumber,
                color = AppTheme.colorScheme.textTertiary,
                style = AppTheme.typography.bodyMedium,
            )
            OtpField(
                value = code,
                readOnly = !uiState.value.timeStarted,
                onValueChange = { code = it }
            )
            if (uiState.value.timeStarted) {
                Text(
                    text = uiState.value.time,
                    color = AppTheme.colorScheme.backgroundStatusErrorSecondary,
                    style = AppTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                )
            } else {
                Text(
                    modifier = Modifier.clickable {
                        eventDispatcher(VerifyContract.UiIntent.ResendCode)
                    },
                    text = stringResource(R.string.auth_enter_sms_code),
                    color = AppTheme.colorScheme.backgroundAccentCyanQuaternary,
                    style = AppTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            NextButton(text = stringResource(R.string.components_next)) {
                eventDispatcher(VerifyContract.UiIntent.OpenHome(code = code))
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    VerifyContent(phoneNumber = "")
}