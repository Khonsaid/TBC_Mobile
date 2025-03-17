package uz.gita.latizx.tbcmobile.ui.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.components.textfield.InputField
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

@Composable
fun CustomDialog(
    text: Int,
    textButton: Int,
    image: Int? = null,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth(),
            colors = CardDefaults.cardColors().copy(containerColor = AppTheme.colorScheme.backgroundPrimary),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (image != null)
                    Image(
                        modifier = Modifier
                            .size(56.dp)
                            .padding(top = 24.dp),
                        painter = painterResource(image),
                        contentDescription = "image"
                    )

                Text(
                    text = stringResource(text),
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colorScheme.textPrimary,
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .height(1.dp)
                        .background(Color.LightGray.copy(alpha = 0.6f))
                )
                Text(
                    text = stringResource(textButton),
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colorScheme.borderBrand,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable(
                            indication = null, interactionSource = null
                        ) { onDismissRequest() },
                )
            }
        }
    }
}

@Composable
fun ConfirmationDialog(
    text: Int,
    image: Int? = null,
    textYesButton: Int,
    textNoButton: Int,
    onClickYes: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth(),
            colors = CardDefaults.cardColors().copy(containerColor = AppTheme.colorScheme.backgroundPrimary),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (image != null)
                    Image(
                        modifier = Modifier
                            .size(56.dp)
                            .padding(top = 24.dp),
                        painter = painterResource(image),
                        contentDescription = "image"
                    )

                Text(
                    text = stringResource(text),
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colorScheme.textPrimary,
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .height(1.dp)
                        .background(Color.LightGray.copy(alpha = 0.6f))
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = stringResource(textYesButton),
                        style = AppTheme.typography.bodyMedium,
                        color = AppTheme.colorScheme.borderBrand,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable(
                                indication = null, interactionSource = null
                            ) { onClickYes() },
                    )
                    Text(
                        text = stringResource(textNoButton),
                        style = AppTheme.typography.bodyMedium,
                        color = AppTheme.colorScheme.borderBrand,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable(
                                indication = null, interactionSource = null
                            ) { onDismissRequest() },
                    )
                }
            }
        }
    }
}

@Composable
fun EditCardNameDialog(
    text: Int,
    image: Int? = null,
    value: String,
    textYesButton: Int,
    textNoButton: Int,
    onClickYes: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var nameCard by remember { mutableStateOf("") }
    Dialog(
        onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth(),
            colors = CardDefaults.cardColors().copy(containerColor = AppTheme.colorScheme.backgroundPrimary),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (image != null)
                    Image(
                        modifier = Modifier
                            .size(56.dp)
                            .padding(top = 24.dp),
                        painter = painterResource(image),
                        contentDescription = "image"
                    )

                Text(
                    text = stringResource(text),
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colorScheme.textPrimary,
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center
                )
                InputField(
                    label = stringResource(R.string.cards_create_card_message),
                    hint = "TBC humo",
                    value = value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .padding(horizontal = 16.dp),
                    onValueChange = {
                        nameCard = it
                    }
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .height(1.dp)
                        .background(Color.LightGray.copy(alpha = 0.6f))
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = stringResource(textYesButton),
                        style = AppTheme.typography.bodyMedium,
                        color = AppTheme.colorScheme.borderBrand,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable(
                                indication = null, interactionSource = null
                            ) { onClickYes(nameCard) },
                    )
                    Text(
                        text = stringResource(textNoButton),
                        style = AppTheme.typography.bodyMedium,
                        color = AppTheme.colorScheme.borderBrand,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable(
                                indication = null, interactionSource = null
                            ) { onDismissRequest() },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {

}