package uz.gita.latizx.comman.model

data class CardsData(
    val id: Int,
    val name: String,
    val amount: Int,
    val expiredMonth: Int,
    val expiredYear: Int,
    val pan: String,
    val owner: String,
    val themeType: Int,
    val isVisible: Boolean,
)