package uz.gita.latizx.entity.retrofit.request.card

import com.google.gson.annotations.SerializedName

data class UpdateCardRequest(
    val id: Int,
    val name: String,
    @SerializedName("theme-type") val themeType: Int,
    @SerializedName("is-visible") val isVisible: String,
)