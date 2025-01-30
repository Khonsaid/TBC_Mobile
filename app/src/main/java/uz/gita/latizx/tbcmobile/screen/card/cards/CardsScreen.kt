package uz.gita.latizx.tbcmobile.screen.card.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.screen.components.topbar.AppTopBar
import uz.gita.latizx.presenter.card.cards.CardsContract
import uz.gita.latizx.presenter.card.cards.CardsViewModelImpl

class CardsScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<CardsViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState()
        CardsScreenContent(
            uiState, viewModel::onEventDispatcher
        )
    }
}

@Composable
private fun CardsScreenContent(
    uiState: State<CardsContract.UIState> = remember { mutableStateOf(CardsContract.UIState) },
    eventDispatcher: (CardsContract.UIIntent) -> Unit = {},
) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                text = R.string.cards_cards,
                hasPrevBtn = false,
                onClickBack = {
                    eventDispatcher(CardsContract.UIIntent.OpenPrevScreen)
                }
            )
        }) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState()),
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.cards_catalog_add_card),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = stringResource(R.string.cards_catalog_add_card_description),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Button(
                    shape = ShapeDefaults.Medium,
                    onClick = { eventDispatcher(CardsContract.UIIntent.OpenAddCardScreen) },
                ) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(R.drawable.ic_plus_24_regular),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(R.string.loan_salary_add_button),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CardsScreenContent()
}