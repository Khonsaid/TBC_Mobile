package uz.gita.latizx.tbcmobile.screen.components.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit

@Composable
fun CircleNumberButton(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = MaterialTheme.typography.displayMedium.fontSize,
    color: Color = MaterialTheme.colorScheme.primary,
    fontWeight: FontWeight? = FontWeight.ExtraBold,
    onClick: () -> Unit,
) {
    Box(modifier) {
        TextButton(
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxSize(),
            onClick = { onClick() }
        ) {
            Text(
                text = text,
                color = color,
                fontSize = fontSize,
                fontWeight = fontWeight
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CircleNumberButton(text = "1", onClick = {})
}