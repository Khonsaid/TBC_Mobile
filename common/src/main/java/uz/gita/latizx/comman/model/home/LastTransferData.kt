package uz.gita.latizx.comman.model.home

data class LastTransferData (
    val amount: Int,
    val from: String,
    val time: Long,
    val to: String,
    val type: String
)