package uz.gita.latizx.entity.mapper

import uz.gita.latizx.comman.model.HistoryItems
import uz.gita.latizx.entity.local.room.entity.HistoryItemsEntity

fun HistoryItemsEntity.toHistoryItems(): HistoryItems = HistoryItems(
    amount = amount,
    from = from,
    time = time,
    to = to,
    type = type
)


fun HistoryItems.toHistoryItemsEntity(): HistoryItemsEntity = HistoryItemsEntity(
    amount = amount,
    from = from,
    time = time,
    to = to,
    type = type
)