package uz.gita.latizx.tbcmobile.screen.card.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.latizx.presenter.card.cards.CardsContract
import uz.gita.latizx.presenter.card.cards.CardsViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.components.items.ItemCardsVirtual
import uz.gita.latizx.tbcmobile.ui.components.topbar.AppTopBar
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

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
    Scaffold(
        containerColor = AppTheme.colorScheme.backgroundPrimary,
        modifier = Modifier.fillMaxSize(),
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
                .fillMaxWidth()
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
                        style = AppTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400),
                        color = AppTheme.colorScheme.textPrimary
                    )
                    Text(
                        text = stringResource(R.string.cards_catalog_add_card_description),
                        style = AppTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400),
                        color = AppTheme.colorScheme.textPrimary
                    )
                }
                Button(
                    shape = ShapeDefaults.Medium,
                    colors = ButtonDefaults.buttonColors().copy(containerColor = AppTheme.colorScheme.backgroundBrandTertiary),
                    onClick = { eventDispatcher(CardsContract.UIIntent.OpenAddCardScreen) },
                ) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(R.drawable.ic_plus_24_regular),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .weight(1f),
                        text = stringResource(R.string.loan_salary_add_button),
                        style = AppTheme.typography.bodySmall,
                    )
                }
            }

            Column(
                modifier = Modifier.padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(Modifier.height(12.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(R.string.cards_catalog_order_physical_card),
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colorScheme.textPrimary
                )
                ItemCardsVirtual(
                    title = R.string.card_order_benefits_info_title,
                    text = R.string.card_order_benefits_info_message_title,
                    image = uz.gita.latizx.comman.R.drawable.ill_credit_card_lg,
                    imgCard = uz.gita.latizx.comman.R.drawable.ag_ps_humo,
                    cardColor = R.color.palette_green_50,
                    onClick = {}
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(R.string.cards_catalog_order_virtual_card),
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colorScheme.textPrimary
                )
                ItemCardsVirtual(
                    title = R.string.cards_details_digital_uzcard,
                    text = R.string.cards_details_digital_visa_title,
                    image = R.drawable.ag_chip_virtual_uzcard,
                    imgCard = uz.gita.latizx.comman.R.drawable.ag_ps_uzpay,
                    cardColor = R.color.palette_blue_60,
                    onClick = {}
                )

                ItemCardsVirtual(
                    title = R.string.cards_details_digital_visa,
                    text = R.string.cards_details_digital_visa_title,
                    image = R.drawable.ag_chip_virtual_visa,
                    imgCard = R.drawable.ag_ps_visa,
                    cardColor = R.color.palette_yellow_60,
                    onClick = {}
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CardsScreenContent()
}