package uz.gita.latizx.tbcmobile.screen.support.support

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.latizx.presenter.support.support.SupportContract
import uz.gita.latizx.presenter.support.support.SupportViewModelImpl
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.components.topbar.AppTopBar
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

class SupportScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<SupportViewModelImpl>()
        SupportScreenContent(viewModel::onEventDispatcher)
    }
}

@Composable
private fun SupportScreenContent(eventDispatcher: (SupportContract.UIIntent) -> Unit = {}) {
    val context = LocalContext.current

    Scaffold(
        containerColor = AppTheme.colorScheme.backgroundPrimary,
        topBar = {
            AppTopBar(
                text = R.string.support_help,
                onClickBack = {
                    eventDispatcher(SupportContract.UIIntent.OpenPrev)
                }
            )
        }
    ) {
        Column(
            Modifier
                .padding(it)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ItemSupport(
                img = R.drawable.ic_chat_message_24_regular,
                title = R.string.support_online_chat,
                onClick = {}
            )
            ItemSupport(
                img = R.drawable.ic_phone,
                title = R.string.support_call_us,
                text = R.string.phone,
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:+99787772727")
                    }
                    context.startActivity(intent)
                }
            )
            ItemSupport(
                img = R.drawable.ic_star_emphasis_32_regular,
                title = R.string.support_feedback_empty,
                onClick = {}
            )
            ItemSupport(
                img = R.drawable.ic_mail_24_regular,
                title = R.string.support_email,
                text = R.string.email,
                onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:contact@tbcbank.uz")
                    }
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Composable
private fun ItemSupport(
    img: Int,
    title: Int,
    text: Int? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding()
            .clickable(indication = null, interactionSource = null) {
                onClick()
            },
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .border(
                    border = BorderStroke(width = 1.dp, color = colorResource(R.color.palette_gray_10)),
                    shape = AppTheme.shape.small
                )
                .padding(12.dp)
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(img),
                tint = AppTheme.colorScheme.borderAccentCyan,
                contentDescription = null
            )
        }
        Column(modifier = Modifier.padding(vertical = 4.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = stringResource(title),
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = AppTheme.colorScheme.textPrimary
            )
            if (text != null)
                Box(
                    modifier = Modifier
                        .background(
                            AppTheme.colorScheme.backgroundSecondary,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(vertical = 4.dp, horizontal = 12.dp)
                ) {
                    Text(
                        text = stringResource(text),
                        style = AppTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        color = AppTheme.colorScheme.textTertiary
                    )
                }
        }
    }
}
