package uz.gita.latizx.tbcmobile.screen.succes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import uz.gita.latizx.comman.model.RecipientData
import uz.gita.latizx.presenter.other.success.SuccessViewModelImpl
import uz.gita.latizx.presenter.success.SuccessContract
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.components.button.AppFilledButton
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme
import uz.gita.latizx.tbcmobile.utils.toFormatCard

class SuccessScreen(private val recipientData: RecipientData) : Screen {
    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val viewModel = getViewModel<SuccessViewModelImpl, SuccessViewModelImpl.Factory>(
            viewModelFactory = { it.onCreate(recipientData = recipientData) }
        )
        val uiState = viewModel.uiState.collectAsState()
        SuccessScreenContent(uiState = uiState, eventDispatcher = viewModel::onEventDispatcher)
    }
}

@Composable
private fun SuccessScreenContent(
    uiState: State<SuccessContract.UIState> = remember { mutableStateOf(SuccessContract.UIState()) },
    eventDispatcher: (SuccessContract.SuccessIntent) -> Unit = {},
) {
    BackHandler(enabled = true) {

    }
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.ill_status_success)
    )

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = AppTheme.colorScheme.backgroundPrimary
    ) {
        Column {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LottieAnimation(
                    modifier = Modifier.size(100.dp),
                    composition = composition,
                    isPlaying = true
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.transfer_transfer_success_message),
                    color = AppTheme.colorScheme.textPrimary,
                    style = AppTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .background(color = AppTheme.colorScheme.backgroundAccentGreen)
                    .padding(vertical = 24.dp, horizontal = 12.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = uiState.value.recipientName,
                    color = AppTheme.colorScheme.textPrimary,
                    style = AppTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = uiState.value.recipientPan.toFormatCard(),
                    color = AppTheme.colorScheme.textPrimary,
                    style = AppTheme.typography.titleSmall
                )

                Spacer(modifier = Modifier.height(32.dp))

                AppFilledButton(
                    text = stringResource(R.string.cards_transfer_close),
                    color = AppTheme.colorScheme.backgroundBrandTertiary,
                    colorText = AppTheme.colorScheme.textOnPrimary,
                    onClick = { eventDispatcher(SuccessContract.SuccessIntent.OpenMoneyTransfersScreen) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SuccessScreenContent()
}