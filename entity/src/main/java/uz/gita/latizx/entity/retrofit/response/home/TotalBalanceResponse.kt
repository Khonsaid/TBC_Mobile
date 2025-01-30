package uz.gita.latizx.entity.retrofit.response.home

import com.google.gson.annotations.SerializedName

data class TotalBalanceResponse(
    @SerializedName("total-balance")
    val totalBalance: Int
)