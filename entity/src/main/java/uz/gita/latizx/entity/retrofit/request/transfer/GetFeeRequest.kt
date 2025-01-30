package uz.gita.latizx.entity.retrofit.request.transfer

import com.google.gson.annotations.SerializedName

data class GetFeeRequest(
    val amount: Int,
    val receiver: String,
    @SerializedName("sender-id")
    val senderId: String,
)