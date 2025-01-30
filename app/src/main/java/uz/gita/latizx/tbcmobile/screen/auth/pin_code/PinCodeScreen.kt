package uz.gita.latizx.tbcmobile.screen.auth.pin_code

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import kotlinx.coroutines.launch
import uz.gita.latizx.tbcmobile.screen.components.button.CircleNumberButton
import uz.gita.latizx.tbcmobile.screen.components.dialog.CustomDialog
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.presenter.auth.pin_code.PinCodeContract
import uz.gita.latizx.presenter.auth.pin_code.PinCodeViewModelImpl

class PinCodeScreen : Screen {
    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    override fun Content() {
        val viewModel = getViewModel<PinCodeViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState()
        PinCodeContent(uiState, viewModel::onEventDispatcher)
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
private fun PinCodeContent(
    uiState: State<PinCodeContract.UiState> = remember { mutableStateOf(PinCodeContract.UiState()) },
    eventDispatcher: (PinCodeContract.UIIntent) -> Unit = {},
) {
    val code by remember { mutableStateOf("") }
    Surface {
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
                    .weight(2.5f)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    eventDispatcher(PinCodeContract.UIIntent.ClickNum(it))
                },
                onClickRemove = {
                    eventDispatcher(PinCodeContract.UIIntent.ClickRemove)
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = {
                    eventDispatcher(PinCodeContract.UIIntent.OpenSignInScreen)
                }) {
                    Text(
                        text = stringResource(R.string.login_change_password),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                }
                TextButton(onClick = {
                    eventDispatcher(PinCodeContract.UIIntent.OpenIntroScreen)
                }) {
                    Text(
                        text = stringResource(R.string.login_log_out),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                }
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
    onClick: (String) -> Unit,
    onClickRemove: () -> Unit,
) {
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
                    onClick = { }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_biometric_24_regular),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
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
                        tint = MaterialTheme.colorScheme.primary
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
                if (isFilled) MaterialTheme.colorScheme.primary
                else Color.Transparent
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                shape = CircleShape
            )
    )
}