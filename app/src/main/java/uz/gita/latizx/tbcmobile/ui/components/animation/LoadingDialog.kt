package uz.gita.latizx.tbcmobile.ui.components.animation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import uz.gita.latizx.tbcmobile.R
import kotlin.collections.get

@Composable
fun LoadingDialog(modifier: Modifier = Modifier, onDismissRequest: () -> Unit = {}) {
    Dialog(
        onDismissRequest = {}
    ) {
        Box(modifier = Modifier) {
            AnimLoading(modifier.align(alignment = Alignment.Center))
        }
    }
}

@Composable
private fun AnimLoading(modifier: Modifier = Modifier, size: Dp = 100.dp, duration: Long = 30L) {
    val images = remember {
        listOf(
            R.drawable.loading_animation_1,
            R.drawable.loading_animation_2,
            R.drawable.loading_animation_3,
            R.drawable.loading_animation_4,
            R.drawable.loading_animation_5,
            R.drawable.loading_animation_6,
            R.drawable.loading_animation_7,
            R.drawable.loading_animation_8,
            R.drawable.loading_animation_9,
            R.drawable.loading_animation_10,
            R.drawable.loading_animation_11,
            R.drawable.loading_animation_12,
            R.drawable.loading_animation_13,
            R.drawable.loading_animation_14,
            R.drawable.loading_animation_15,
            R.drawable.loading_animation_16,
            R.drawable.loading_animation_17,
            R.drawable.loading_animation_18,
            R.drawable.loading_animation_19,
            R.drawable.loading_animation_20,
            R.drawable.loading_animation_21,
            R.drawable.loading_animation_22,
            R.drawable.loading_animation_23,
            R.drawable.loading_animation_24,
            R.drawable.loading_animation_25,
            R.drawable.loading_animation_26,
            R.drawable.loading_animation_27,
            R.drawable.loading_animation_28,
            R.drawable.loading_animation_29,
            R.drawable.loading_animation_30,
            R.drawable.loading_animation_31,
            R.drawable.loading_animation_32,
            R.drawable.loading_animation_33,
            R.drawable.loading_animation_34,
            R.drawable.loading_animation_35,
            R.drawable.loading_animation_36,
            R.drawable.loading_animation_37,
            R.drawable.loading_animation_38,
            R.drawable.loading_animation_39,
            R.drawable.loading_animation_40,
            R.drawable.loading_animation_41,
            R.drawable.loading_animation_42,
            R.drawable.loading_animation_43,
            R.drawable.loading_animation_44,
            R.drawable.loading_animation_45,
            R.drawable.loading_animation_46,
            R.drawable.loading_animation_47,
            R.drawable.loading_animation_48,
            R.drawable.loading_animation_49,
            R.drawable.loading_animation_50,
            R.drawable.loading_animation_51,
            R.drawable.loading_animation_52,
            R.drawable.loading_animation_53,
            R.drawable.loading_animation_54,
            R.drawable.loading_animation_55,
            R.drawable.loading_animation_56,
            R.drawable.loading_animation_57,
            R.drawable.loading_animation_58,
            R.drawable.loading_animation_59,
            R.drawable.loading_animation_60,
            R.drawable.loading_animation_61,
            R.drawable.loading_animation_62,
            R.drawable.loading_animation_63,
            R.drawable.loading_animation_64,
            R.drawable.loading_animation_65,
            R.drawable.loading_animation_66,
            R.drawable.loading_animation_67,
            R.drawable.loading_animation_68,
            R.drawable.loading_animation_69,
            R.drawable.loading_animation_70,
            R.drawable.loading_animation_71,
            R.drawable.loading_animation_72,
            R.drawable.loading_animation_73,
            R.drawable.loading_animation_74,
            R.drawable.loading_animation_75,
            R.drawable.loading_animation_76,
            R.drawable.loading_animation_77,
            R.drawable.loading_animation_78,
            R.drawable.loading_animation_79
        )
    }

    val duration = duration
    var currentIndex by remember { mutableIntStateOf(0) }

    // Animatsiya davomiyligini hisoblash va indeksni o'zgartirish
    LaunchedEffect(key1 = currentIndex) {
        delay(duration)
        currentIndex = (currentIndex + 1) % images.size
    }

    // Hozirgi rasmni chizish
    Box(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
    ) {
        val painter = painterResource(id = images[currentIndex])
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(size) // Rasm o'lchamini moslashtirish
        )
    }
}