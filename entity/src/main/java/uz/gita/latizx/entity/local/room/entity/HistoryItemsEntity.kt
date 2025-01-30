package uz.gita.latizx.entity.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryItemsEntity(
    @PrimaryKey val time: Long,
    val amount: Int,
    val from: String,
    val to: String,
    val type: String,
)