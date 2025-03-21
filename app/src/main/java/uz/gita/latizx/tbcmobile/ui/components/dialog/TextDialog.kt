package uz.gita.latizx.tbcmobile.ui.components.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

@Composable
fun TextDialog(
    text: String,
    onDismiss: () -> Unit = {},
) {
    var isVisible by remember { mutableStateOf(true) }

    if (isVisible) {
        Dialog(onDismissRequest = { isVisible == false }) {
            Card(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                colors = CardDefaults.cardColors().copy(containerColor = AppTheme.colorScheme.backgroundPrimary),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = text,
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colorScheme.textPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Justify
                )
            }
            LaunchedEffect(Unit) {
                delay(4000)
                isVisible = false
                onDismiss()
            }
        }
    }
}