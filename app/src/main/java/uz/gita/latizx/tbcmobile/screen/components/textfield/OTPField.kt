package uz.gita.latizx.tbcmobile.screen.components.textfield

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OtpField(
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    value: String = "",
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            if (it.length <= 6) {
                val filterValue = it.filter { char -> char.isDigit() }
                onValueChange(filterValue)
            }
        },
        readOnly = readOnly,
        maxLines = 6,
        modifier = modifier
            .padding(16.dp)
            .width(200.dp)
            .height(52.dp),
        shape = ShapeDefaults.Medium,
        visualTransformation = CodeVisualTransformation(),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
            focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center
        )
    )
}

private class CodeVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val spanStyleNumber = SpanStyle(
            color = Color(0xFF0A9AA4),
            fontSize = 16.sp,  // Kattalashtirilgan font
            fontWeight = FontWeight.Bold  // Qalinroq font
        )
        val spanStyleX = SpanStyle(
            color = Color.LightGray,
            fontSize = 16.sp,  // Kattalashtirilgan font
            fontWeight = FontWeight.Bold  // Qalinroq font
        )

        var formatted = buildAnnotatedString {
            var index = 0
            repeat(6) { i ->
                if (index < text.length) {
                    withStyle(spanStyleNumber) {
                        append(text[index++])
                    }
                } else {
                    withStyle(spanStyleX) {
                        append('―')
                    }
                }
                // Oxirgi element emas bo'lsa, probel qo'shamiz
                if (i < 5) append("   ")  // Uch probel bilan ajratish
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (text.isEmpty()) return 0
                return (offset * 4).coerceIn(0, formatted.length)  // Har bir raqam uchun 4 ta pozitsiya (raqam + 3 probel)
            }

            override fun transformedToOriginal(offset: Int): Int {
                return (offset / 4).coerceIn(0, text.length)  // Har 4 ta pozitsiyaga 1 ta original pozitsiya
            }
        }

        return TransformedText(formatted, offsetMapping)
    }
}
