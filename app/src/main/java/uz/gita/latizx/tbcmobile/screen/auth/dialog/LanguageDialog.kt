package uz.gita.latizx.tbcmobile.screen.auth.dialog

import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import uz.gita.latizx.tbcmobile.R
import uz.gita.latizx.tbcmobile.ui.theme.AppTheme

@Parcelize
data class LanguageDialog(val lang: String) : Screen, Parcelable {
    @IgnoredOnParcel
    var onClickLang: (String) -> Unit = {}

    @IgnoredOnParcel
    var onDismissRequest: () -> Unit = {}

    @Composable
    override fun Content() {
        LanguageBottomSheet(
            lang = lang,
            onClickLang = onClickLang,
            onDismissRequest = onDismissRequest
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguageBottomSheet(
    lang: String,
    onClickLang: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(AppTheme.colorScheme.backgroundTertiary)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Color.Gray)
                .padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(R.string.intro_choose_your_language),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 16.dp),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onTertiary
        )
        ContainerLang(
            text = "O'zbek (Lotin)",
            R.drawable.ag_flag_uz,
            lang = lang,
            check = "uz",
            onClickLang = onClickLang,
            onDismissRequest = onDismissRequest
        )
        ContainerLang(
            text = "Русский",
            R.drawable.ag_flag_ru,
            lang = lang,
            check = "ru",
            onClickLang = onClickLang,
            onDismissRequest = onDismissRequest
        )
        ContainerLang(
            text = "English",
            R.drawable.ag_flag_us,
            lang = lang,
            check = "en",
            onClickLang = onClickLang,
            onDismissRequest = onDismissRequest
        )
    }
}

@Composable
private fun ContainerLang(
    text: String,
    image: Int,
    lang: String,
    check: String,
    onClickLang: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (check == lang) AppTheme.colorScheme.backgroundBrand
                else Color.Transparent
            )
            .clickable {
                onClickLang(check)
                onDismissRequest()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(
            selected = lang == check,
            colors = RadioButtonDefaults.colors(
                selectedColor = AppTheme.colorScheme.backgroundBrandTertiary,
                unselectedColor = AppTheme.colorScheme.backgroundBrandTertiary
            ),
            onClick = {}
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(image),
            contentDescription = "flag $check",
//                modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            textAlign = TextAlign.Center,
            text = text,
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
//    IntroScreenContent()
    LanguageBottomSheet("UZ",
        onDismissRequest = {},
        onClickLang = {})
}