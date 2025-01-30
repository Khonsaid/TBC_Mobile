package uz.gita.latizx.entity.retrofit.request.home

import com.google.gson.annotations.SerializedName

data class UpdateInfoRequest(
    @SerializedName("born-date")
    val bornDate: String,
    @SerializedName("first-name")
    val firstName: String,
    @SerializedName("gender-type")
    val genderType: String,
    @SerializedName("last-name")
    val lastName: String
)