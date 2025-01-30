package uz.gita.latizx.entity.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.latizx.entity.local.room.dao.HistoryItemsDao
import uz.gita.latizx.entity.local.room.entity.HistoryItemsEntity

@Database(entities = [HistoryItemsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getHistoryItemsDao(): HistoryItemsDao
}