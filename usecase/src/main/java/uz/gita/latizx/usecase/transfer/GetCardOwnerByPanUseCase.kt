package uz.gita.latizx.usecase.transfer

import kotlinx.coroutines.flow.Flow
import uz.gita.latizx.comman.model.transfer.GetCardOwnerByPanData

interface GetCardOwnerByPanUseCase {
    fun invoke(pan: String): Flow<Result<GetCardOwnerByPanData>>
}