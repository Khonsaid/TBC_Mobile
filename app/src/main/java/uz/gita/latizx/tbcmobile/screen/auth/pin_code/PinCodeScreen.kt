package uz.gita.latizx.tbcmobile.screen.auth.pin_code

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.latizx.presenter.auth.pin_code.PinCodeContract
import uz.gita.latizx.presenter.auth.pin_code.PinCodeViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.components.animation.LoadingDialog
import uz.gita.latizx.tbcmobile.ui.components.button.CircleNumberButton
import uz.gita.latizx.tbcmobile.ui.components.dialog.CustomDialog
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme
import uz.gita.latizx.tbcmobile.utils.BiometricAuthenticator

class PinCodeScreen(private val setPinCode: Boolean) : Screen {
    @OptIn(ExperimentalVoyagerApi::class)
    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    override fun Content() {
        val viewModel = getViewModel<PinCodeViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState()
        PinCodeContent(uiState, viewModel::onEventDispatcher, setPinCode)

        var showDialog by remember { mutableStateOf(false) }
        var showLoading by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            viewModel._sideEffect.collect {
                showLoading = it.showLoading
                showDialog = it.showErrorDialog
            }
        }
        if (showLoading) LoadingDialog()
        if (showDialog) {
            CustomDialog(
                text = R.string.signing_change_password_dialog_title,
                image = R.drawable.fingerprint_dialog_error,
                textButton = R.string.signing_close,
                onDismissRequest = {
                    viewModel.onEventDispatcher(PinCodeContract.UIIntent.DismissErrorDialog)
                }
            )
        }
    }
}

@Composable
private fun PinCodeContent(
    uiState: State<PinCodeContract.UiState> = remember { mutableStateOf(PinCodeContract.UiState()) },
    eventDispatcher: (PinCodeContract.UIIntent) -> Unit = {},
    setPinCode: Boolean = false,
) {

    Surface(color = AppTheme.colorScheme.backgroundPrimary) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(R.drawable.img_brand_logo_splash),
                    contentDescription = "logo splash",
                )
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(R.drawable.img_brand_text_splash),
                    contentDescription = "text splash",
                )
            }

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center,
            ) {
                PinIndicators(
                    codeArray = uiState.value.codeArray
                )
            }

            BoxNumbers(
                Modifier
                    .fillMaxWidth()
                    .weight(3f)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    eventDispatcher(PinCodeContract.UIIntent.ClickNum(it, setPinCode))
                },
                biometricSuccess = {
                    eventDispatcher(PinCodeContract.UIIntent.BiometricSuccess)
                },
                onClickRemove = {
                    eventDispatcher(PinCodeContract.UIIntent.ClickRemove)
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.clickable(indication = null, interactionSource = null) {
                        eventDispatcher(PinCodeContract.UIIntent.OpenSignInScreen)
                    },
                    text = stringResource(R.string.login_change_password),
                    color = AppTheme.colorScheme.borderBrand,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )

                Text(
                    modifier = Modifier.clickable(indication = null, interactionSource = null) {
                        eventDispatcher(PinCodeContract.UIIntent.OpenIntroScreen)
                    },
                    text = stringResource(R.string.login_log_out),
                    color = AppTheme.colorScheme.borderBrand,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PinCodeContent()
}

@Composable
private fun BoxNumbers(
    modifier: Modifier,
    biometricSuccess: () -> Unit,
    onClick: (String) -> Unit,
    onClickRemove: () -> Unit,
) {
    val context = LocalContext.current
    val biometricAuthenticator = BiometricAuthenticator(context)
    val activity = context as? FragmentActivity
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                CircleNumberButton(modifier = Modifier.weight(1f), text = "1") { onClick("1") }
                CircleNumberButton(modifier = Modifier.weight(1f), text = "2") { onClick("2") }
                CircleNumberButton(modifier = Modifier.weight(1f), text = "3") { onClick("3") }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                CircleNumberButton(modifier = Modifier.weight(1f), text = "4") { onClick("4") }
                CircleNumberButton(modifier = Modifier.weight(1f), text = "5") { onClick("5") }
                CircleNumberButton(modifier = Modifier.weight(1f), text = "6") { onClick("6") }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                CircleNumberButton(modifier = Modifier.weight(1f), text = "7") { onClick("7") }
                CircleNumberButton(modifier = Modifier.weight(1f), text = "8") { onClick("8") }
                CircleNumberButton(modifier = Modifier.weight(1f), text = "9") { onClick("9") }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier
                        .clip(CircleShape)
                        .fillMaxSize()
                        .weight(1f),
                    onClick = {
                        if (activity != null) {
                            biometricAuthenticator.promptBiometricAuth(
                                title = "Login",
                                subTitle = "Use your fingerprint",
                                negativeButtonText = "Cancel",
                                fragmentActivity = activity,
                                onSuccess = {
                                    biometricSuccess()
                                },
                                onError = { _, errorString ->

                                },
                                onFailed = {

                                }
                            )
                        }
                    }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_biometric_24_regular),
                        contentDescription = null,
                        tint = AppTheme.colorScheme.borderBrand,
                    )
                }
                CircleNumberButton(modifier = Modifier.weight(1f), text = "0") { onClick("0") }
                IconButton(
                    modifier = Modifier
                        .clip(CircleShape)
                        .fillMaxSize()
                        .weight(1f),
                    onClick = { onClickRemove() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_chevron_left_24_regular),
                        contentDescription = null,
                        tint = AppTheme.colorScheme.borderBrand,
                    )
                }
            }
        }
    }
}

@Composable
private fun PinIndicators(codeArray: List<Boolean>) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Absolute.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        codeArray.forEach { isFilled ->
            PinIndicator(isFilled = isFilled)
        }
    }
}

@Composable
private fun PinIndicator(isFilled: Boolean) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(16.dp)
            .clip(CircleShape)
            .background(
                if (isFilled) AppTheme.colorScheme.borderBrand
                else Color.Transparent
            )
            .border(
                width = 2.dp,
                color = AppTheme.colorScheme.borderBrand,
                shape = CircleShape
            )
    )
}