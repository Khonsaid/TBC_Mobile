package uz.gita.latizx.tbcmobile.ui.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import uz.gita.latizx.tbcmobile.R

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
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = MaterialTheme.colorScheme.onTertiary,
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
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
                TextButton(
                    onClick = { onDismissRequest() }
                ) {
                    Text(
                        text = stringResource(textButton),
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        modifier = Modifier.padding(16.dp),
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