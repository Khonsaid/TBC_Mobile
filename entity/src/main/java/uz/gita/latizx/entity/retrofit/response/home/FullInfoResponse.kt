package uz.gita.latizx.entity.retrofit.response.home

import com.google.gson.annotations.SerializedName

data class FullInfoResponse(
    @SerializedName("born-date")
    val bornDate: Long,
    @SerializedName("first-name")
    val firstName: String,
    val gender: Int,
    @SerializedName("last-name")
    val lastName: String,
    val phone: String,
)