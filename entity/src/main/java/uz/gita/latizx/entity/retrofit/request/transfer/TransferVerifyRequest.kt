package uz.gita.latizx.entity.retrofit.request.transfer

data class TransferVerifyRequest(
    val code: String,
    val token: String
)