package uz.gita.latizx.entity.retrofit.response.home

data class LastTransferResponse(
    val amount: Int,
    val from: String,
    val time: Long,
    val to: String,
    val type: String
)