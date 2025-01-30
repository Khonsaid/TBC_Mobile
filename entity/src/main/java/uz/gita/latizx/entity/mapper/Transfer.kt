package uz.gita.latizx.entity.mapper

import uz.gita.latizx.comman.model.GetFeeData
import uz.gita.latizx.comman.model.transfer.GetCardOwnerByPanData
import uz.gita.latizx.entity.retrofit.response.transfer.GetCardOwnerByPanResponse
import uz.gita.latizx.entity.retrofit.response.transfer.GetFeeResponse

fun GetCardOwnerByPanResponse.toDate() = GetCardOwnerByPanData(
    pan = pan
)

fun GetFeeResponse.toDate() = GetFeeData(
    amount = amount,
    fee = fee
)
