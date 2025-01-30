package uz.gita.latizx.tbcmobile.screen.auth.sign_up

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import kotlinx.coroutines.launch
import uz.gita.latizx.presenter.auth.sign_up.SignUpContract
import uz.gita.latizx.tbcmobile.screen.components.button.NextButton
import uz.gita.latizx.tbcmobile.screen.components.dialog.CustomDialog
import uz.gita.latizx.tbcmobile.screen.components.dialog.DatePickerFieldToModal
import uz.gita.latizx.tbcmobile.screen.components.textfield.InputField
import uz.gita.latizx.tbcmobile.screen.components.textfield.PasswordInputField
import uz.gita.latizx.tbcmobile.screen.components.textfield.PhoneInputField
import uz.gita.latizx.tbcmobile.screen.components.topbar.AppTopBar
import uz.gita.latizx.presenter.auth.sign_up.SignUpViewModelImpl
import uz.gita.latizx.tbcmobile.R

@OptIn(ExperimentalVoyagerApi::class)
class SignUpScreen : Screen {
    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    override fun Content() {
        val viewModel = getViewModel<SignUpViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState()
        SignInScreenContext(uiState, viewModel::onEventDispatcher)

        val scope = rememberCoroutineScope()
        var showDialog by remember { mutableStateOf(false) }
        var dialogMessage by remember { mutableIntStateOf(0) }
        scope.launch {
            viewModel._sideEffect.collect {
                dialogMessage = it.msg
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
private fun SignInScreenContext(
    uiState: State<SignUpContract.UIState> = remember { mutableStateOf(SignUpContract.UIState()) },
    eventDispatcher: (SignUpContract.UIIntent) -> Unit = {},
) {
    val inputName = remember { mutableStateOf("") }
    val inputLastName = remember { mutableStateOf("") }
    val inputDate = remember { mutableLongStateOf(0L) }
    val inputPhone = remember { mutableStateOf("") }
    val inputPassword = remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
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
                    label = "Ismingizni kiritin",
                    hint = "Vali",
                    onValueChange = {
                        inputName.value = it
                    }
                )
                InputField(
                    label = "Familiyangizni kiriting",
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
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.signing_the_number_is_used_to_contact_with_client),
                    modifier = Modifier.weight(1f),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily(Font(R.font.roboto_regular))
                )

                Text(
                    text = stringResource(R.string.signing_fully),
                    modifier = Modifier.clickable { },
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.roboto_bold))
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