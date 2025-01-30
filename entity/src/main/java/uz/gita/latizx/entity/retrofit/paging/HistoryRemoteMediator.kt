package uz.gita.latizx.entity.retrofit.paging

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import uz.gita.latizx.entity.local.room.AppDatabase
import uz.gita.latizx.entity.local.room.entity.HistoryItemsEntity
import uz.gita.latizx.entity.retrofit.api.TransferAPI
import uz.gita.latizx.entity.utils.isNetworkAvailable

@OptIn(ExperimentalPagingApi::class)
class HistoryRemoteMediator(
    private val db: AppDatabase,
    private val transferAPI: TransferAPI,
    private val context: Context,
) : RemoteMediator<Int, HistoryItemsEntity>() {
    val dao = db.getHistoryItemsDao()
    val isNetworkAvailable = context.isNetworkAvailable()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HistoryItemsEntity>,
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    lastItem?.time?.toInt()?.plus(1) ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }
            val size = state.config.pageSize
            val response = transferAPI.getHistory(size = size, currentPage = currentPage).body()

            val historyItems = response?.child?.map {
                HistoryItemsEntity(
                    amount = it.amount,
                    from = it.from,
                    time = it.time,
                    to = it.to,
                    type = it.type
                )
            } ?: emptyList()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dao.clearAll()
                }
                dao.insertAll(historyItems)
            }
            MediatorResult.Success(endOfPaginationReached = historyItems.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}