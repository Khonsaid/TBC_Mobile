package uz.gita.latizx.comman.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VerifyData(
    val password: String?,
    val phone: String,
) : Parcelable
