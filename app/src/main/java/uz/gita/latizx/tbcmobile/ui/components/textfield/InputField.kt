package uz.gita.latizx.tbcmobile.ui.components.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

/*Input text*/
@Composable
fun InputField(
    label: String,
    value: String = "",
    hint: String,
    readOnly: Boolean = false,
    modifier: Modifier = Modifier.padding(horizontal = 16.dp),
    modifierTextField: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    var inputData = remember { mutableStateOf(value) }
    Column(modifier = modifier) {
        Text(
            text = label,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            color = AppTheme.colorScheme.textPrimary
        )

        OutlinedTextField(
            value = inputData.value,
            onValueChange = {
                inputData.value = it
                onValueChange.invoke(it)
            },
            modifier = modifierTextField
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .height(52.dp),
            placeholder = {
                Text(
                    text = hint,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = AppTheme.colorScheme.textStatusInfo,
                    textAlign = TextAlign.Center
                )
            },
            textStyle = TextStyle(textAlign = TextAlign.Start, fontSize = 16.sp),
            readOnly = readOnly,
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = AppTheme.colorScheme.borderBrand,
                focusedBorderColor = AppTheme.colorScheme.borderBrand,
            )
        )
    }
}