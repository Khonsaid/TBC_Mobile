package uz.gita.latizx.tbcmobile.ui.components.textfield

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

@Composable
fun PhoneInputField(
    @StringRes
    text: Int = R.string.signing_enter_your_mobile_number,
    modifier: Modifier = Modifier.padding(horizontal = 16.dp),
    onValueChange: (String) -> Unit,
) {
    var phoneNumber by remember { mutableStateOf("") }
    Column(modifier = modifier) {
        Text(
            text = stringResource(text),
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            color = AppTheme.colorScheme.textPrimary
        )
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                if (it.length <= 9) {
                    val filteredValue = it.filter { char -> char.isDigit() }
                    phoneNumber = filteredValue
                    onValueChange(filteredValue)
                }
            },
            maxLines = 9,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PhoneVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = AppTheme.colorScheme.borderBrand,
                focusedBorderColor = AppTheme.colorScheme.borderBrand,
            ),
            trailingIcon = {
                if (phoneNumber.isNotEmpty()) {
                    IconButton(onClick = {
                        phoneNumber = ""
                        onValueChange("")
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_mtrl_chip_close_circle),
                            contentDescription = "clear phone",
                            tint = AppTheme.colorScheme.backgroundAccentCoolGraySecondary
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            textStyle = TextStyle(textAlign = TextAlign.Start, fontSize = MaterialTheme.typography.bodyMedium.fontSize),
            prefix = {
                Text(
                    "+998 ",
                    color = AppTheme.colorScheme.textStatusInfo,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                )
            },
        )
    }
}

private class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val mask = "XX XXX XXXX"
        var formatted = buildAnnotatedString {
            var index = 0

            mask.forEach { char ->
                when {
                    char.isLetter() && index < text.length -> {
                        // Kiritilgan raqamlar uchun rang
                        withStyle(style = SpanStyle(color = Color(0xFF0A9AA4))) {
                            append(text[index++])
                        }
                    }

                    char.isLetter() -> {
                        withStyle(style = SpanStyle(color = Color(0xFF4BD2DC))) {
                            append('X')
                        }
                    }  // X belgisi uchun default rang
                    else -> append(char)  // Probel uchun
                }
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (text.isEmpty()) return 0
                if (offset > text.length) return formatted.length

                // Probellarni hisoblab offsetni qaytarish
                var count = 0
                for (i in 0 until offset) {
                    count++
                    if (i == 2 || i == 5) count++
                }
                return count.coerceIn(0, formatted.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (formatted.isEmpty()) return 0
                if (offset > formatted.length) return text.length

                // Probellarni ayirib offsetni qaytarish
                var count = 0
                var originalOffset = 0
                while (count < offset && count < formatted.length) {
                    if (formatted[count] != ' ') originalOffset++
                    count++
                }
                return originalOffset.coerceIn(0, text.length)
            }
        }

        return TransformedText(formatted, offsetMapping)
    }
}