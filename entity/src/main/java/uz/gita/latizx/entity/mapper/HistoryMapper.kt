package uz.gita.latizx.entity.mapper

import uz.gita.latizx.comman.model.HistoryItemsData
import uz.gita.latizx.entity.local.room.entity.HistoryItemsEntity

fun HistoryItemsEntity.toHistoryItems(): HistoryItemsData = HistoryItemsData(
    amount = amount,
    from = from,
    time = time,
    to = to,
    type = type
)


fun HistoryItemsData.toHistoryItemsEntity(): HistoryItemsEntity = HistoryItemsEntity(
    amount = amount,
    from = from,
    time = time,
    to = to,
    type = type
)