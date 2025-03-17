package uz.gita.latizx.tbcmobile.ui.components.text

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

@Composable
fun SuperScriptText(
    normalText: String,
    normalFonSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
    superText: String,
    color: Color = AppTheme.colorScheme.iconBrand,
    superTextFontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
    superTextFontWeight: FontWeight = FontWeight.Normal,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = normalFonSize,
                        color = color
                    )
                ) {
                    append(normalText)
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = superTextFontSize,
                        fontWeight = superTextFontWeight,
                        baselineShift = BaselineShift.Superscript,
                        color = color
                    )
                ) {
//                    append(" ")
                    append(superText)
                }
            },
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center
        )
    }
}