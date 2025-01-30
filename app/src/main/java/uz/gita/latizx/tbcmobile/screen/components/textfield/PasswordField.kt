package uz.gita.latizx.tbcmobile.screen.components.textfield

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import uz.gita.latizx.tbcmobile.R


/*Password Input*/
@Composable
fun PasswordInputField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier.padding(horizontal = 16.dp),
    onValueChange: (String) -> Unit,
) {
    var passwordVisible = remember { mutableStateOf(false) }
    var passwordData = remember { mutableStateOf("") }

    Column(
        modifier = modifier
    ) {
        Text(
            text = text,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
        )

        OutlinedTextField(
            value = passwordData.value,
            onValueChange = {
                passwordData.value = it
                onValueChange.invoke(it)
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .height(56.dp),
            placeholder = {
                Text(
                    text = hint,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                )
            },
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (passwordVisible.value)
                VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            trailingIcon = {
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordVisible.value)
                                R.drawable.ic_eye
                            else R.drawable.ic_eye_off
                        ),
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
            )
        )
    }
}