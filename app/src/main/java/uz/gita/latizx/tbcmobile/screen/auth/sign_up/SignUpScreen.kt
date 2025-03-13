package uz.gita.latizx.tbcmobile.screen.auth.sign_up

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
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
import uz.gita.latizx.presenter.auth.sign_up.SignUpContract
import uz.gita.latizx.presenter.auth.sign_up.SignUpViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.components.animation.LoadingDialog
import uz.gita.latizx.tbcmobile.ui.components.button.NextButton
import uz.gita.latizx.tbcmobile.ui.components.dialog.CustomDialog
import uz.gita.latizx.tbcmobile.ui.components.dialog.DatePickerFieldToModal
import uz.gita.latizx.tbcmobile.ui.components.textfield.InputField
import uz.gita.latizx.tbcmobile.ui.components.textfield.PasswordInputField
import uz.gita.latizx.tbcmobile.ui.components.textfield.PhoneInputField
import uz.gita.latizx.tbcmobile.ui.components.topbar.AppTopBar
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

@OptIn(ExperimentalVoyagerApi::class)
class SignUpScreen : Screen {
    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    override fun Content() {
        val viewModel = getViewModel<SignUpViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState()
        SignInScreenContext(uiState, viewModel::onEventDispatcher)

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

        val text = when (dialogMessage) {
            1 -> R.string.signing_sign_up_the_first_name_empty
            2 -> R.string.signing_sign_up_the_last_name_empty
            3 -> R.string.signing_enter_birth_date
            4 -> R.string.support_phone_number_wrong_format
            5 -> R.string.signing_sign_up_the_password_format_is_not_correct
            else -> R.string.components_server_error
        }

        if (showErrorDialog) {
            CustomDialog(
                text = text,
                image = R.drawable.fingerprint_dialog_error,
                textButton = R.string.signing_close,
                onDismissRequest = { viewModel.onEventDispatcher(SignUpContract.UIIntent.DismissErrorDialog) }
            )
        }
        if (showMoreInfo) {
            CustomDialog(
                text = R.string.signing_the_number_is_used_to_contact_with_client,
                image = R.drawable.ic_info_circle_24_regular,
                textButton = R.string.signing_close,
                onDismissRequest = { viewModel.onEventDispatcher(SignUpContract.UIIntent.DismissMoreInfoDialog) }
            )
        }
        if (uiState.value.showLoading) LoadingDialog()
    }
}

@Composable
private fun SignInScreenContext(
    uiState: State<SignUpContract.UIState> = remember { mutableStateOf(SignUpContract.UIState()) },
    eventDispatcher: (SignUpContract.UIIntent) -> Unit = {},
) {
    val inputName = remember { mutableStateOf("") }
    val inputLastName = remember { mutableStateOf("") }
    val inputDate = remember { mutableLongStateOf(0L) }
    val inputPhone = remember { mutableStateOf("") }
    val inputPassword = remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppTheme.colorScheme.backgroundPrimary
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            AppTopBar(
                text = R.string.intro_registration,
                onClickBack = {
                    eventDispatcher(SignUpContract.UIIntent.OpenPrev)
                }
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InputField(
                    label = stringResource(R.string.signing_sign_up_the_first_name_empty),
                    hint = "Vali",
                    onValueChange = {
                        inputName.value = it
                    }
                )
                InputField(
                    label = stringResource(R.string.signing_sign_up_the_last_name_empty),
                    hint = "Valiyev",
                    onValueChange = {
                        inputLastName.value = it
                    }
                )

                DatePickerFieldToModal(
                    onDateSelected = { inputDate.longValue = it }
                )

                PhoneInputField(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    inputPhone.value = it
                }
                PasswordInputField(
                    text = stringResource(R.string.signing_enter_password),
                    hint = "Parol kiriting",
                    onValueChange = {
                        inputPassword.value = it
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            // Bottom text and button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.signing_the_number_is_used_to_contact_with_client),
                    modifier = Modifier.weight(1f),
                    color = AppTheme.colorScheme.textSecondary,
                    style = AppTheme.typography.captionLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = stringResource(R.string.signing_fully),
                    modifier = Modifier.clickable(
                        indication = ripple(bounded = true), interactionSource = remember { MutableInteractionSource() }
                    ) { eventDispatcher.invoke(SignUpContract.UIIntent.ShowModeInfoDialog) },
                    color = AppTheme.colorScheme.backgroundBrandTertiary,
                    style = AppTheme.typography.captionLarge,
                )
            }
            NextButton(text = stringResource(R.string.components_next)) {
                eventDispatcher(
                    SignUpContract.UIIntent.OpenVerify(
                        firstName = inputName.value,
                        lastName = inputLastName.value,
                        bornDate = inputDate.longValue,
                        phone = "+998${inputPhone.value}",
                        password = inputPassword.value
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SignInScreenContext()
}