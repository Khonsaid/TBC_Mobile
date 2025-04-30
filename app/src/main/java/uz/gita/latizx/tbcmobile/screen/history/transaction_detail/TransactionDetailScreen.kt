package uz.gita.latizx.tbcmobile.screen.history.transaction_detail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.latizx.comman.formatWithSeparator
import uz.gita.latizx.comman.model.HistoryItemsData
import uz.gita.latizx.presenter.history.transaction_detail.TransactionDetailContract
import uz.gita.latizx.presenter.history.transaction_detail.TransactionDetailViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.components.image.IconForItems
import uz.gita.latizx.tbcmobile.ui.components.topbar.AppTopBar
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme
import uz.gita.latizx.tbcmobile.utils.formatDateTime
import uz.gita.latizx.tbcmobile.utils.generateAndSharePdf
import kotlin.text.uppercase

class TransactionDetailScreen(private val historyData: HistoryItemsData) : Screen {
    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        Log.d("TTT", "TransactionDetailScreen Content: $historyData")
        val viewModel = getViewModel<TransactionDetailViewModelImpl, TransactionDetailViewModelImpl.Factory>(
            viewModelFactory = {
                it.onCreate(historyData = historyData)
            }
        )
        val uiState = viewModel.uiState.collectAsState()
        TransactionDetailScreenContent(uiState = uiState, eventDispatcher = viewModel::onEventDispatcher)
    }
}

@Composable
private fun TransactionDetailScreenContent(
    uiState: State<TransactionDetailContract.UIState> = remember { mutableStateOf(TransactionDetailContract.UIState()) },
    eventDispatcher: (TransactionDetailContract.UIIntent) -> Unit = {},
) {
    val context = LocalContext.current

    Scaffold(
        containerColor = AppTheme.colorScheme.backgroundPrimary,
        topBar = {
            AppTopBar(
                text = R.string.transactions_details_title,
                hasPrevBtn = true,
                onClickBack = {
                    eventDispatcher.invoke(TransactionDetailContract.UIIntent.OpenHomeScreen)
                },
                onClickPrev = {
                    eventDispatcher.invoke(TransactionDetailContract.UIIntent.OpenPrevScreen)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            IconForItems(sizeBox = 64.dp, icon = R.drawable.ic_card_transfer_24_regular)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (uiState.value.historyData?.from == "9860080808090010") stringResource(R.string.transfer_card_between_own_cards)
                else uiState.value.historyData?.to?.uppercase().toString(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    color = AppTheme.colorScheme.textPrimary,
                    fontWeight = FontWeight.W400,
                    fontSize = AppTheme.typography.titleSmall.fontSize,
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontStyle = AppTheme.typography.titleLarge.fontStyle, fontSize = 36.sp, fontWeight = FontWeight.W600)) {
                        append(
                            if (uiState.value.historyData?.type == "income")
                                "    +${uiState.value.historyData?.amount.toString().formatWithSeparator()}"
                            else "     -${uiState.value.historyData?.amount.toString().formatWithSeparator()}"
                        )
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = AppTheme.typography.bodyMedium.fontSize, // UZS kichikroq bo‘ladi
                            fontWeight = FontWeight.W400,
                            baselineShift = BaselineShift.Superscript // UZS yuqoriga chiqadi
                        )
                    ) {
                        append(" UZS")
                    }
                },
                color = AppTheme.colorScheme.textPrimary
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = uiState.value.historyData?.time?.formatDateTime().toString(),
                fontWeight = FontWeight.W500,
                fontSize = AppTheme.typography.bodyMedium.fontSize,
                color = AppTheme.colorScheme.textPrimary,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(modifier = Modifier.size(24.dp), painter = painterResource(R.drawable.ill_status_success), contentDescription = null)
                Text(
                    text = stringResource(R.string.transaction_completed_status),
                    style = TextStyle(
                        color = AppTheme.colorScheme.borderStatusSuccess,
                        fontWeight = FontWeight.W400,
                        fontSize = AppTheme.typography.bodyMedium.fontSize,
                    )
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(color = AppTheme.colorScheme.backgroundAccentCoolGray, shape = AppTheme.shape.large)
                    .clickable(indication = ripple(bounded = true, radius = 30.dp),
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        context.generateAndSharePdf(uiState.value.historyData)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(painter = painterResource(R.drawable.ic_share_android_24_regular), contentDescription = null, tint = AppTheme.colorScheme.borderStatusSuccess)
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.transaction_details_share_action_title),
                fontWeight = FontWeight.W400,
                fontSize = AppTheme.typography.bodyMedium.fontSize,
                color = AppTheme.colorScheme.textPrimary,
            )
            Spacer(modifier = Modifier.height(18.dp))
            ItemDerail(title = R.string.transaction_detail_terminal_id, text = "EP${System.currentTimeMillis()}", hasBgColor = true)
            ItemDerail(title = R.string.transaction_detail_fiscal_sign, text = System.currentTimeMillis().toString(), hasBgColor = false)
            ItemDerail(title = R.string.transaction_detail_receiver_bank, text = "GITA", hasBgColor = true)
            ItemDerail(title = R.string.transaction_detail_receiver, text = uiState.value.historyData?.to.toString(), hasBgColor = false)
            ItemDerail(title = R.string.transaction_detail_sender, text = uiState.value.historyData?.from.toString(), hasBgColor = true)
        }
    }
}

@Composable
private fun ItemDerail(title: Int, text: String, hasBgColor: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = if (hasBgColor) AppTheme.colorScheme.backgroundAccentCoolGray else Color.Transparent)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(title),
            style = TextStyle(
                color = AppTheme.colorScheme.textPrimary,
                fontWeight = FontWeight.W400,
                fontSize = AppTheme.typography.bodySmall.fontSize,
            )
        )
        Text(
            text = text.uppercase(),
            style = TextStyle(
                color = AppTheme.colorScheme.textPrimary,
                fontWeight = FontWeight.W600,
                fontSize = AppTheme.typography.bodySmall.fontSize,
            ),
            color = AppTheme.colorScheme.textPrimary,
        )
    }
}