package uz.gita.latizx.tbcmobile.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import uz.gita.latizx.comman.LocationHelper
import uz.gita.latizx.comman.model.HistoryItemsData
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
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

fun String.toFormatCard(): String {
    return this.reversed().chunked(4).joinToString(" ").reversed()
}


fun String.toFormatMoney(hasTail: Boolean = false): String {
    val parts = this.split(".") // **Bo‘laklarga ajratish**
    val mainPart = parts[0].reversed().chunked(3).joinToString(" ").reversed() // **Raqamlarni formatlash**
    val decimalPart = when {
        parts.size > 1 -> {
            val part1 = if (parts[1].length > 4) parts[1].substring(0, 2) else parts[1]
            ".$part1"
        } // **Agar vergulli qism bo‘lsa, uni qo‘shish**
        hasTail -> ".00" // **hasTail = true bo‘lsa, .00 qo‘shish**
        else -> ""
    }

    return mainPart + decimalPart
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

fun Long.formatDateTime(): String {
    val locale: Locale = Locale(LocationHelper.getLang())
    // Agar vaqt sekundlarda bo‘lsa, millisekundga o‘tkazamiz
    val correctTime = if (this < 1000000000000L) this * 1000 else this

    val date = Date(correctTime)
    val dateFormat = SimpleDateFormat("dd MMM yyyy / HH:mm", locale)
    return dateFormat.format(date)
}

fun Modifier.parallaxLayoutModifier(scrollState: ScrollState, rate: Int) =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val height = if (rate > 0) scrollState.value / rate else scrollState.value
        layout(placeable.width, placeable.height) {
            placeable.place(0, height)
        }
    }

fun String.getCountryCodeByCurrency(): String? {
    val currencyToCountry = mapOf(
        "USD" to "US", "EUR" to "EU", "RUB" to "RU", "KZT" to "KZ", "GBP" to "GB",
        "JPY" to "JP", "AUD" to "AU", "UZS" to "UZ", "CNY" to "CN", "AED" to "AE",
        "AFN" to "AF", "ARS" to "AR", "AMD" to "AM", "AZN" to "AZ", "BGN" to "BG",
        "BRL" to "BR", "BYN" to "BY", "CAD" to "CA", "CHF" to "CH", "CZK" to "CZ",
        "DKK" to "DK", "EGP" to "EG", "GEL" to "GE", "HKD" to "HK", "HUF" to "HU",
        "INR" to "IN", "IDR" to "ID", "ILS" to "IL", "IRR" to "IR", "IQD" to "IQ",
        "ISK" to "IS", "JOD" to "JO", "KRW" to "KR", "KWD" to "KW", "LAK" to "LA",
        "LBP" to "LB", "LYD" to "LY", "MDL" to "MD", "MXN" to "MX", "MYR" to "MY",
        "NOK" to "NO", "NZD" to "NZ", "OMR" to "OM", "PKR" to "PK", "PLN" to "PL",
        "QAR" to "QA", "RON" to "RO", "RSD" to "RS", "SAR" to "SA", "SDG" to "SD",
        "SEK" to "SE", "SGD" to "SG", "SYP" to "SY", "THB" to "TH", "TJS" to "TJ",
        "TMT" to "TM", "TRY" to "TR", "UAH" to "UA", "UYU" to "UY", "VES" to "VE",
        "VND" to "VN", "ZAR" to "ZA", "TND" to "TN", "YER" to "YE", "BDT" to "BD",
        "BHD" to "BH", "BND" to "BN",
        "KGS" to "KG",  // 🇰🇬 Qirg‘iziston
        "KHR" to "KH",  // 🇰🇭 Kambodja
        "XDR" to "IMF", // 🌍 Xalqaro Valyuta Fondi (IMF)
        "PHP" to "PH",  // 🇵🇭 Filippin
        "MNT" to "MN",  // 🇲🇳 Mo‘g‘uliston
        "MAD" to "MA",  // 🇲🇦 Marokash
        "DZD" to "DZ",  // 🇩🇿 Jazoir
        "CUP" to "CU"   // 🇨🇺 Kuba
    )
    return currencyToCountry[this]?.lowercase() // Valyuta kodiga mos davlat kodini qaytaradi
}

fun Context.generateAndSharePdf(history: HistoryItemsData?) {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    val canvas: Canvas = page.canvas

    val paint = Paint()
    paint.color = Color.BLACK
    paint.textSize = 14f
    paint.textAlign = Paint.Align.LEFT

    var yPos = 60f

    // Chek sarlavhasi
    paint.textSize = 18f
    paint.isFakeBoldText = true
    canvas.drawText("💳 To'lov Cheki", 220f, yPos, paint)
    yPos += 30f

    // Chiziq
    paint.strokeWidth = 2f
    canvas.drawLine(50f, yPos, 550f, yPos, paint)
    yPos += 30f

    // Ma'lumotlar
    paint.textSize = 14f
    paint.isFakeBoldText = false
    canvas.drawText("📅 Sana: ${SimpleDateFormat("dd MMM yyyy / HH:mm", Locale.getDefault()).format(Date(history?.time ?: 0))}", 50f, yPos, paint)
    yPos += 20f
    canvas.drawText("💰 Summa: ${history?.amount} UZS", 50f, yPos, paint)
    yPos += 20f
    canvas.drawText("🏦 Kimdan: ${history?.from}", 50f, yPos, paint)
    yPos += 20f
    canvas.drawText("🏦 Kimga: ${history?.to}", 50f, yPos, paint)
    yPos += 20f
    canvas.drawText("ℹ️ Status: ${if (history?.type == "income") "Kirim ✅" else "Chiqim ❌"}", 50f, yPos, paint)
    yPos += 30f

    // Qizil chiziq pastda
    paint.color = Color.RED
    canvas.drawLine(50f, yPos, 550f, yPos, paint)
    yPos += 30f

    // Ilova nomi
    paint.color = Color.BLACK
    paint.textSize = 12f
    canvas.drawText("TBC Mobile Bank", 220f, yPos, paint)

    pdfDocument.finishPage(page)

    // **Vaqtinchalik fayl yaratish**
    val tempFile = File.createTempFile("transaction_receipt", ".pdf", this.cacheDir)
    tempFile.deleteOnExit() // Ilovadan chiqishda avtomatik o‘chiriladi

    try {
        pdfDocument.writeTo(FileOutputStream(tempFile))
    } catch (e: IOException) {
        e.printStackTrace()
        Toast.makeText(this, "PDF yaratishda xatolik!", Toast.LENGTH_SHORT).show()
        return
    } finally {
        pdfDocument.close()
    }

    // **PDF ulashish**
    val uri = FileProvider.getUriForFile(this, "${this.packageName}.provider", tempFile)
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    this.startActivity(Intent.createChooser(shareIntent, "To‘lov Chekini Ulashish"))
}