package uz.gita.latizx.entity.retrofit.response.home

import com.google.gson.annotations.SerializedName

data class BasicInfoResponse(
    val age: Int,
    @SerializedName("firsrt-name")
    val firstName: String,
    @SerializedName("gender-type")
    val genderType: Int
)