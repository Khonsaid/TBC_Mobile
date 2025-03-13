package uz.gita.latizx.tbcmobile.screen.auth.sign_in

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.latizx.presenter.auth.sign_in.SignInContract
import uz.gita.latizx.presenter.auth.sign_in.SignInViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.components.animation.LoadingDialog
import uz.gita.latizx.tbcmobile.ui.components.button.NextButton
import uz.gita.latizx.tbcmobile.ui.components.dialog.CustomDialog
import uz.gita.latizx.tbcmobile.ui.components.textfield.PasswordInputField
import uz.gita.latizx.tbcmobile.ui.components.textfield.PhoneInputField
import uz.gita.latizx.tbcmobile.ui.components.topbar.AppTopBar
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

@OptIn(ExperimentalVoyagerApi::class)
class SignInScreen : Screen {
    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    override fun Content() {
        val viewModel = getViewModel<SignInViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState()
        SignUpScreenContent(uiState, viewModel::onEventDispatcher)

        var showMoreInfo by remember { mutableStateOf(false) }
        var showErrorDialog by remember { mutableStateOf(false) }
        var dialogMessage by remember { mutableIntStateOf(0) }
        LaunchedEffect(Unit) {
            viewModel._sideEffect.collect {
                dialogMessage = it.message
                showMoreInfo = it.showMoreInfo
                showErrorDialog = it.showErrorDialog
            }
        }
        if (showErrorDialog) {
            CustomDialog(
                text = if (dialogMessage == 1) R.string.support_phone_number_wrong_format else if (dialogMessage == 2) R.string.signing_change_password_dialog_title else R.string.story_nothing_found,
                image = R.drawable.fingerprint_dialog_error,
                textButton = R.string.signing_close,
                onDismissRequest = { viewModel.onEventDispatcher(SignInContract.UiIntent.DismissErrorDialog) }
            )
        }
        if (showMoreInfo) {
            CustomDialog(
                text = R.string.signing_the_number_is_used_to_contact_with_client,
                image = R.drawable.ic_info_circle_24_regular,
                textButton = R.string.signing_close,
                onDismissRequest = { viewModel.onEventDispatcher(SignInContract.UiIntent.DismissMoreInfoDialog) }
            )
        }
        if (uiState.value.showLoading) LoadingDialog()
    }
}

@Composable
private fun SignUpScreenContent(
    uiState: State<SignInContract.UiState> = remember { mutableStateOf(SignInContract.UiState()) },
    eventDispatcher: (SignInContract.UiIntent) -> Unit = {},
) {
    var password = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppTheme.colorScheme.backgroundPrimary
    ) {
        Column {
            AppTopBar(
                text = R.string.intro_begin,
                onClickBack = {
                    eventDispatcher(SignInContract.UiIntent.OpenPrevScreen)
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                PhoneInputField(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    phoneNumber.value = it
                }
                PasswordInputField(
                    text = stringResource(R.string.signing_enter_password),
                    hint = stringResource(R.string.signing_enter_password),
                    onValueChange = {
                        password.value = it
                    }
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            // Bottom text and button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.signing_the_number_is_used_to_contact_with_client),
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    color = AppTheme.colorScheme.textSecondary,
                    style = AppTheme.typography.captionLarge,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = stringResource(R.string.signing_fully),
                    modifier = Modifier.clickable(
                        indication = ripple(bounded = true), interactionSource = remember { MutableInteractionSource() }
                    ) { eventDispatcher.invoke(SignInContract.UiIntent.ShowModeInfoDialog) },
                    color = AppTheme.colorScheme.backgroundBrandTertiary,
                    style = AppTheme.typography.captionLarge
                )
            }
            NextButton(
                text = stringResource(R.string.components_next),
            ) {
                eventDispatcher(
                    SignInContract.UiIntent.OpenVerifyScreen(
                        phone = "+998${phoneNumber.value}", password = password.value
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SignUpScreenContent()
}