package uz.gita.latizx.entity.retrofit.response.auth

import com.google.gson.annotations.SerializedName

data class UpdateTokenResponse(
    @SerializedName("refresh-token") val refreshToken: String,
    @SerializedName("access-token") val accessToken: String,
)