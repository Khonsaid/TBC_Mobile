package uz.gita.latizx.tbcmobile.screen.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R

@Composable
fun AppFilledButton(
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = ShapeDefaults.Medium,
    onClick: () -> Unit,
) {
    Box(modifier = modifier) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = shape,
            onClick = onClick
        ) {
            Text(
                text = text,
//            color = Color.White,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            )
        }
    }
}

@Composable
fun AppOutlinedButton(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(56.dp),
    shape: Shape = ShapeDefaults.Medium,
    text: String,
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier,
        shape = shape,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        onClick = onClick,
    ) {
        Text(
            text = text,
//            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
        )
    }
}


/* Button next */
@Composable
fun NextButton(
    text: String,
    onClickButton: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE0E3E8))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    onClickButton()
                }
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .height(56.dp)
                .background(MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(end = 8.dp)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_right_regular),
                contentDescription = text,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Column {
        AppFilledButton(text = "FilledButton") {}
        AppOutlinedButton(text = "AppOutlinedButton") {}
        NextButton("Next Button") {}
    }
}