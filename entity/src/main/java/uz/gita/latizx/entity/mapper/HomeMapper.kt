package uz.gita.latizx.entity.mapper

import uz.gita.latizx.comman.model.home.BasicInfoData
import uz.gita.latizx.comman.model.home.FullInfoData
import uz.gita.latizx.comman.model.home.LastTransferData
import uz.gita.latizx.comman.model.home.TotalBalanceData
import uz.gita.latizx.entity.retrofit.response.home.BasicInfoResponse
import uz.gita.latizx.entity.retrofit.response.home.FullInfoResponse
import uz.gita.latizx.entity.retrofit.response.home.LastTransferResponse
import uz.gita.latizx.entity.retrofit.response.home.TotalBalanceResponse

fun TotalBalanceResponse.toData() = TotalBalanceData(
    totalBalance = totalBalance
)

fun BasicInfoResponse.toData() = BasicInfoData(
    age = age,
    firstName = firstName,
    genderType = genderType
)

fun FullInfoResponse.toData() = FullInfoData(
    bornDate = bornDate,
    firstName = firstName,
    gender = gender,
    lastName = lastName,
    phone = phone,
)

fun LastTransferResponse.toData() = LastTransferData(
    amount = amount,
    from = from,
    time = time,
    to = to,
    type = type
)