package uz.gita.latizx.entity.retrofit.request.card

import com.google.gson.annotations.SerializedName

data class AddCardRequest(
    val name: String,
    val pan: String,
    @SerializedName("expired-month")
    val expiredMonth: String,
    @SerializedName("expired-year")
    val expiredYear: String,
)