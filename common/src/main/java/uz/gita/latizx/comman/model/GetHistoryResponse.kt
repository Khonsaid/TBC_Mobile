package uz.gita.latizx.comman.model

import com.google.gson.annotations.SerializedName

data class GetHistoryResponse(
    @SerializedName("total-elements") val totalElements : Int,
    @SerializedName("total-pages") val totalPages : Int,
    @SerializedName("current-page") val currentPage : Int,
    val child : List<HistoryItems>
)

data class HistoryItems(
    val amount: Int,
    val from: String,
    val time: Long,
    val to: String,
    val type: String
)