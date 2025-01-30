package uz.gita.latizx.tbcmobile.utils

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.ScrollState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


fun Configuration.getSectionHeight(): Dp {
    val screenHeight = this.screenHeightDp.dp // Ekran balandligi (dp)
//    val firstSectionHeight = screenHeight * 0.5f        // Birinchi qism uchun 50% balandlik
    return screenHeight
}

fun String.formatCard(): String {
    return this.reversed().chunked(4).joinToString(" ").reversed()
}

@SuppressLint("NewApi")
fun Long.formatToHour(): String {
    // Create a Date object using the timestamp
    val instant = Instant.ofEpochMilli(this)
    // Convert Instant to LocalDateTime based on the system's default timezone
    val dateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
    // Format the date and time as needed
    val formatter = DateTimeFormatter.ofPattern("HH:mm")// Format the time as Hours:Minutes:Seconds
    return dateTime.format(formatter)
}

fun Long.formatToDate(): String {
    // Create a Date object using the timestamp
    val date = Date(this)

    // Define the desired format
    val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()) // Format: Day Month Year
    return formatter.format(date)
}

fun Modifier.parallaxLayoutModifier(scrollState: ScrollState, rate: Int) =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val height = if (rate > 0) scrollState.value / rate else scrollState.value
        layout(placeable.width, placeable.height) {
            placeable.place(0, height)
        }
    }