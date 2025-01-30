package uz.gita.latizx.entity.retrofit.request.transfer

import com.google.gson.annotations.SerializedName

data class TransferRequest(
    val amount: Int,
    @SerializedName("receiver-pan")
    val receiverPan: String,
    @SerializedName("sender-id")
    val senderId: String,
    val type: String
)