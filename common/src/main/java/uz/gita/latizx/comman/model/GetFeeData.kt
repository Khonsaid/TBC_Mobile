package uz.gita.latizx.comman.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetFeeData(
    val amount: Int,
    val fee: Int
) : Parcelable