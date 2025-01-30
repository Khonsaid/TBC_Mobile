package uz.gita.latizx.entity.retrofit.request.auth

import com.google.gson.annotations.SerializedName

data class UpdateTokenRequest(
    @SerializedName("refresh-token")
    val refreshToken: String,
)
