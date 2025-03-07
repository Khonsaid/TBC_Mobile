package uz.gita.latizx.comman.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetHistoryResponse(
    @SerializedName("total-elements") val totalElements: Int,
    @SerializedName("total-pages") val totalPages: Int,
    @SerializedName("current-page") val currentPage: Int,
    val child: List<HistoryItemsData>,
)

@Parcelize
data class HistoryItemsData(
    val amount: Int,
    val from: String,
    val time: Long,
    val to: String,
    val type: String,
) : Parcelable