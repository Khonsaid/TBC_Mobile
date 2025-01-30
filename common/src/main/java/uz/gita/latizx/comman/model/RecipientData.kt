package uz.gita.latizx.comman.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipientData(
    val recipientPan: String,
    val recipientName: String
) : Parcelable
