package uz.gita.latizx.entity.mapper

import uz.gita.latizx.comman.model.CardsData
import uz.gita.latizx.entity.retrofit.response.card.CardsResponse

fun CardsResponse.toData() = CardsData(
    id = id,
    name = name,
    amount = amount,
    expiredMonth = expiredMonth,
    expiredYear = expiredYear,
    pan = pan,
    owner = owner,
    themeType = themeType,
    isVisible = isVisible,
)


