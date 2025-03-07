package uz.gita.latizx.usecase.transfer

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.gita.latizx.comman.model.HistoryItemsData

interface GetHistoryUseCase {
    fun invoke() : Flow<PagingData<HistoryItemsData>>
}