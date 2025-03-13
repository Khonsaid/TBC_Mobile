package uz.gita.latizx.comman

import java.util.Locale
import kotlin.text.isBlank
import kotlin.text.isEmpty
import kotlin.text.matches
import kotlin.text.replace
import kotlin.text.toRegex

fun String.isValidPhoneNumber(): Boolean {
    val sanitizedPhone = this.replace(" ", "")
    return when {
        sanitizedPhone.isEmpty() -> false
        !sanitizedPhone.matches(".*[0-9].*".toRegex()) -> false
//        !sanitizedPhone.startsWith("+998") -> false to "Telefon raqami 998 bilan boshlanishi kerak"
        sanitizedPhone.length < 13 -> false
        else -> true
    }
}

fun String.isValidPassword(): Boolean {
    return when {
        this.isEmpty() || this.isBlank() -> false
        this.length < 8 -> false
//        !this.matches(".*[0-9].*".toRegex()) -> false to "Parolda kamida bitta raqam bo'lishi kerak!"
        else -> true
    }
}

fun String.isValidName(): Boolean {
    return when {
        this.isEmpty() || this.isBlank() -> false
//        this.length < 8 -> false to "Parol 8 ta belgidan oshmasligi kerak!"
//        !this.matches(".*[0-9].*".toRegex()) -> false to "Parolda kamida bitta raqam bo'lishi kerak!"
        else -> true
    }
}

fun Long.isValidDate(): Boolean {
    if (this == 0L) return false
    val millisecondsInOneYear: Long = 365L * 24 * 60 * 60 * 1000    // 1 yil=365×24×60×60×1000=31,536,000,000 millisekund
    val sixteenYearsInMilliseconds: Long = 16L * millisecondsInOneYear //16×31,536,000,000=504,576,000,000 millisekund
    return System.currentTimeMillis() - this >= sixteenYearsInMilliseconds
}

fun Int.formatTime(): String {
    val minutes = this / 60
    val remainingSeconds = this % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, remainingSeconds)
}

fun String.formatWithSeparator(): String {
    return this.reversed().chunked(3).joinToString(" ").reversed()
}
