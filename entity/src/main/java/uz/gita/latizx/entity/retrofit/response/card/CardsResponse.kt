package uz.gita.latizx.entity.retrofit.response.card

import com.google.gson.annotations.SerializedName

data class CardsResponse(
    val id: Int,
    val name: String,
    val amount: Int,
    @SerializedName("expired-month")
    val expiredMonth: Int,
    @SerializedName("expired-year")
    val expiredYear: Int,
    val pan: String,
    val owner: String,
    @SerializedName("theme-type")
    val themeType: Int,
    @SerializedName("is-visible")
    val isVisible: Boolean,
)