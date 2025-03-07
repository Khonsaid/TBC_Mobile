package uz.gita.latizx.tbcmobile.ui.components.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme
import uz.gita.latizx.tbcmobile.R

@Composable
fun CustomSearch(hint:String,modifierBox: Modifier = Modifier, onValueChange: (String) -> Unit) {
    var searchValue by remember { mutableStateOf("") }
    Box(
        modifier = modifierBox
            .background(
                color = AppTheme.colorScheme.backgroundTertiary,
                shape = AppTheme.shape.small
            )
    ) {
        BasicTextField(
            value = searchValue,
            onValueChange = {
                searchValue = it
                onValueChange(it)
            },
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 12.dp),
            singleLine = true,
            textStyle = TextStyle(
                color = AppTheme.colorScheme.textPrimary,
                fontSize = 14.sp
            ),
            cursorBrush = SolidColor(AppTheme.colorScheme.iconBrandSecondary),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = AppTheme.colorScheme.textPrimary.copy(alpha = 0.6f),
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Box(modifier = Modifier.weight(1f)) {
                        // Show hint when empty
                        if (searchValue.isEmpty()) {
                            Text(
                                text = hint,
                                color = AppTheme.colorScheme.textPrimary.copy(alpha = 0.5f),
                                fontSize = 14.sp
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun SearchPreview() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red)
    ) {
        CustomSearch(hint = stringResource(R.string.search_menu_title)){}
    }
}