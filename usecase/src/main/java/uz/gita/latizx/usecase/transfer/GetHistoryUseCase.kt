package uz.gita.latizx.usecase.transfer

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.gita.latizx.comman.model.HistoryItems
import uz.gita.latizx.entity.local.room.entity.HistoryItemsEntity

interface GetHistoryUseCase {
    fun invoke() : Flow<PagingData<HistoryItems>>
}