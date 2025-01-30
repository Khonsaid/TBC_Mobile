package uz.gita.latizx.tbcmobile.screen.card.add

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import kotlinx.coroutines.launch
import uz.gita.latizx.tbcmobile.screen.card.dialog.DateSelectDialog
import uz.gita.latizx.tbcmobile.screen.components.animation.LoadingDialog
import uz.gita.latizx.tbcmobile.screen.components.button.AppFilledButton
import uz.gita.latizx.tbcmobile.screen.components.dialog.TextDialog
import uz.gita.latizx.tbcmobile.screen.components.textfield.CardInputField
import uz.gita.latizx.tbcmobile.screen.components.textfield.EmptyDrawField
import uz.gita.latizx.tbcmobile.screen.components.textfield.InputField
import uz.gita.latizx.tbcmobile.screen.components.topbar.AppTopBar
import uz.gita.latizx.presenter.card.add.AddCardContract
import uz.gita.latizx.presenter.card.add.AddCardViewModelImpl
import uz.gita.latizx.tbcmobile.R

class AddCardScreen : Screen {
    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    override fun Content() {
        val viewModel = getViewModel<AddCardViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState()
        BottomSheetNavigator {
            AddCardScreenContent(uiState = uiState, eventDispatcher = viewModel::onEventDispatcher)
        }

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
            TextDialog(
                text = dialogMessage,
            )
        }
        if(uiState.value.showLoading) LoadingDialog()
    }
}

@Composable
private fun AddCardScreenContent(
    uiState: State<AddCardContract.UIState> = remember { mutableStateOf(AddCardContract.UIState()) },
    eventDispatcher: (AddCardContract.UIIntent) -> Unit = {},
) {
    var pan by remember { mutableStateOf("") }
    var expiredYear by remember { mutableStateOf("") }
    var expiredMonth by remember { mutableStateOf("") }
    var nameCard by remember { mutableStateOf("") }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                text = R.string.cards_add_a_card,
                onClickBack = {
                    eventDispatcher(AddCardContract.UIIntent.OpenPrevScreen)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(top = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.color_surface)
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_card_scan_24_regular),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        text = stringResource(R.string.cards_add_scan),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            CardInputField(
                text = R.string.cards_add_card_card_number,
                onValueChange = { pan = it }
//                onSubmit = {pan = it}
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(R.string.cards_add_card_validity_period),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    EmptyDrawField(
                        value = expiredMonth,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            eventDispatcher(AddCardContract.UIIntent.ClickExpiredMonth)
                        }
                    )
                    EmptyDrawField(
                        value = expiredYear,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            eventDispatcher(AddCardContract.UIIntent.ClickExpiredYear)
                        }
                    )
                }
            }
            InputField(
                label = stringResource(R.string.cards_create_card_message),
                hint = "TBC humo",
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    nameCard = it
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            AppFilledButton(
                modifier = Modifier.padding(bottom = 24.dp),
                text = stringResource(R.string.loan_salary_add_button),
                onClick = {
                    eventDispatcher(
                        AddCardContract.UIIntent.AddCard(
                            pan = pan,
                            expiredYear = expiredYear,
                            expiredMonth = expiredMonth,
                            name = nameCard
                        )
                    )
                }
            )
        }

        val bottomSheetNavigator = LocalBottomSheetNavigator.current

        if (uiState.value.isBottomSheetVisible) {
            val dialog = DateSelectDialog(uiState.value.listData)
            bottomSheetNavigator.show(dialog)
            dialog.onDismissRequest = {
                if (uiState.value.listData.size == 12) expiredMonth = it
                else expiredYear = it
                eventDispatcher(AddCardContract.UIIntent.HideBottomSheet)
                bottomSheetNavigator.hide()
            }
        }
        if (bottomSheetNavigator.isVisible) eventDispatcher(AddCardContract.UIIntent.HideBottomSheet)
    }
}

@Preview
@Composable
private fun Preview() {
    AddCardScreenContent()
}