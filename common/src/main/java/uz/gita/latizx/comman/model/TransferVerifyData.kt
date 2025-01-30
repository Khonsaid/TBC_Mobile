package uz.gita.latizx.comman.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransferVerifyData(
    val amount: Int,
    val receiverPan: String,
    val senderId: String,
    val type: String,
) : Parcelable