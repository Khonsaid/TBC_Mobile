package uz.gita.latizx.tbcmobile.ui.components.textfield

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.platform.LocalFocusManager
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
import androidx.compose.ui.unit.sp
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

@Composable
fun CardInputField(
    @StringRes
    text: Int,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
//    onSubmit: (String) -> Unit
) {
    var cardNumber by remember { mutableStateOf("") }
    val localFocusManager = LocalFocusManager.current // Fokusni boshqarish uchun

    Column(modifier = modifier) {
        Text(
            text = stringResource(text),
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            color = AppTheme.colorScheme.textPrimary
        )
        OutlinedTextField(
            value = cardNumber,
            onValueChange = {
                if (it.length <= 16) {
                    val filteredValue = it.filter { char -> char.isDigit() }
                    cardNumber = filteredValue
                    onValueChange(filteredValue)

                    // Maksimal uzunlikka yetganda yuborish va klaviaturani yopish
                    if (filteredValue.length == 16) {
                        localFocusManager.clearFocus() // Klaviaturani yopish
//                        onSubmit(filteredValue) // Ma'lumotni yuborish
                    }
                }
            },
            textStyle = TextStyle(textAlign = TextAlign.Start, fontSize = 16.sp),
            maxLines = 9,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = CardVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor =  AppTheme.colorScheme.borderBrand,
                focusedBorderColor =  AppTheme.colorScheme.borderBrand,
            ),
            trailingIcon = {
                if (cardNumber.isNotEmpty()) {
                    IconButton(onClick = {
                        cardNumber = ""
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
            keyboardActions = KeyboardActions(
                onDone = {
                    if (cardNumber.length == 16) {
                        localFocusManager.clearFocus() // Klaviaturani yopish
//                        onSubmit(cardNumber) // Ma'lumotni yuborish
                    }
                }
            ),
            singleLine = true,
        )
    }
}

private class CardVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val mask = "XXXX XXXX XXXX XXXX"

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
                    if (i % 4 == 0) count++
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