package uz.gita.latizx.entity.retrofit.request.auth

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    val phone: String,
    val password: String,
    @SerializedName("first-name") val firstName: String,
    @SerializedName("last-name") val lastName: String,
    @SerializedName("born-date") val bornDate: String,
    val gender: String
)