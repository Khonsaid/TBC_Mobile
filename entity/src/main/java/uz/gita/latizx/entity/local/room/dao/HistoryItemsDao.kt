package uz.gita.latizx.entity.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.gita.latizx.entity.local.room.entity.HistoryItemsEntity

@Dao
interface HistoryItemsDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(items: List<HistoryItemsEntity>)

    @Query("SELECT * FROM HistoryItemsEntity ORDER BY time DESC")
    fun pagingSource(): PagingSource<Int, HistoryItemsEntity>

    @Query("DELETE FROM HistoryItemsEntity")
    suspend fun clearAll()
}