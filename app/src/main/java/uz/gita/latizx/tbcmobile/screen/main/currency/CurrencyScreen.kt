package uz.gita.latizx.tbcmobile.screen.main.currency

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.latizx.presenter.currency.CurrencyContract
import uz.gita.latizx.presenter.currency.CurrencyViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.components.animation.LoadingDialog
import uz.gita.latizx.tbcmobile.ui.components.button.CalculatorTab
import uz.gita.latizx.tbcmobile.ui.components.items.CurrencyTBC
import uz.gita.latizx.tbcmobile.ui.components.items.ItemCalculate
import uz.gita.latizx.tbcmobile.ui.components.items.ItemCurrCurrency
import uz.gita.latizx.tbcmobile.ui.components.topbar.AppTopBar
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

class CurrencyScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<CurrencyViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState()
        CurrencyScreenContent(uiState = uiState, viewModel::onEventDispatcher)

        var showDialog by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            viewModel._sideEffect.collect { effect ->
                showDialog = effect.showLoading
            }
        }
        if (showDialog) {
            LoadingDialog()
        }
    }
}

@Composable
private fun CurrencyScreenContent(
    uiState: State<CurrencyContract.UIState> = remember { mutableStateOf(CurrencyContract.UIState()) },
    eventDispatcher: (CurrencyContract.UIIntent) -> Unit = {},
) {
    Scaffold(
        containerColor = AppTheme.colorScheme.backgroundPrimary,
        topBar = {
            AppTopBar(text = R.string.my_space_rates_title, onClickBack = { eventDispatcher.invoke(CurrencyContract.UIIntent.OpenPrev) })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Spacer(Modifier.height(16.dp))
                CalculatorTab(uiState.value.isCurrency, onClick = {
                    eventDispatcher(CurrencyContract.UIIntent.ChangeScreen)
                })
                if (uiState.value.isCurrency)
                    CurrencyTBC(if (uiState.value.exchangeRates.isNotEmpty()) uiState.value.exchangeRates[0] else null)
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 12.dp)
                    .fillMaxWidth()
                    .background(color = if (uiState.value.isCurrency) AppTheme.colorScheme.backgroundAccentCoolGray else Color.Transparent)

            ) {
                if (uiState.value.isCurrency) {
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.rates_exchange_official_rate),
                                style = AppTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Start,
                                color = AppTheme.colorScheme.textPrimary
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        items(uiState.value.exchangeRates.size) { index ->
                            ItemCurrCurrency(uiState.value.exchangeRates[index])
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        Column {
                            ItemCalculate(fromUZS = uiState.value.fromUZS,
                                rate = if (uiState.value.fromUZS) uiState.value.rateUZS else uiState.value.rateUSD,
                                value = if (uiState.value.fromUZS) uiState.value.valueUZS else uiState.value.valueUSD,
                                onValueChange = {
                                    eventDispatcher.invoke(CurrencyContract.UIIntent.InputSum(it))
                                })
                            Spacer(modifier = Modifier.height(8.dp))
                            ItemCalculate(fromUZS = !uiState.value.fromUZS,
                                rate = if (!uiState.value.fromUZS) uiState.value.rateUZS else uiState.value.rateUSD,
                                value = if (!uiState.value.fromUZS) uiState.value.valueUZS else uiState.value.valueUSD,
                                readOnly = true,
                                onValueChange = {})
                        }
                        // O'rtadagi iconButton
                        Box(
                            modifier = Modifier.matchParentSize(),  // Box o'lchamini ota-element bilan tenglash
                            contentAlignment = Alignment.CenterStart   // Contentni o'ng-o'rtaga joylashtirish
                        ) {
                            IconButton(
                                onClick = {
                                    eventDispatcher.invoke(CurrencyContract.UIIntent.ChangeCalculate)
                                },
                                modifier = Modifier
                                    .padding(start = 64.dp)  // O'ng tomondan padding berish
                                    .background(color = AppTheme.colorScheme.backgroundPrimary, shape = CircleShape)
                                    .size(32.dp)  // width va height o'rniga size ishlatish mumkin
                                    .padding(6.dp)
                            ) {
                                Icon(
                                    painterResource(R.drawable.ic_arrow_swap_horizontal_24_regular),
                                    contentDescription = null,
                                    tint = AppTheme.colorScheme.backgroundAccentCoolGraySecondary,
                                    modifier = Modifier.rotate(90f)
                                )
                            }
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    Column(
                        Modifier
                            .background(color = AppTheme.colorScheme.backgroundAccentCoolGray)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_info_circle_24_regular),
                            contentDescription = null, Modifier.size(32.dp),
                            tint = AppTheme.colorScheme.backgroundAccentCoolGraySecondary
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            stringResource(R.string.rates_exchange_calculator_warning_text),
                            style = AppTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            color = AppTheme.colorScheme.textPrimary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
