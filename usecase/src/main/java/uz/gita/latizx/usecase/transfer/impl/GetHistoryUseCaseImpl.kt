package uz.gita.latizx.usecase.transfer.impl

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.gita.latizx.comman.model.HistoryItemsData
import uz.gita.latizx.entity.mapper.toHistoryItems
import uz.gita.latizx.entity.repository.TransferRepository
import uz.gita.latizx.usecase.transfer.GetHistoryUseCase
import javax.inject.Inject

class GetHistoryUseCaseImpl @Inject constructor(
    private val transferRepository: TransferRepository,
) : GetHistoryUseCase {
    override fun invoke(): Flow<PagingData<HistoryItemsData>> = transferRepository.getHistory().map { pagingData ->
        pagingData.map { it.toHistoryItems() }
    }
}