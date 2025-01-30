package uz.gita.latizx.usecase.transfer.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import uz.gita.latizx.comman.model.transfer.GetCardOwnerByPanData
import uz.gita.latizx.entity.retrofit.request.transfer.GetCardOwnerByPanRequest
import uz.gita.latizx.entity.repository.TransferRepository
import uz.gita.latizx.usecase.transfer.GetCardOwnerByPanUseCase
import javax.inject.Inject

class GetCardOwnerByPanUseCaseImpl @Inject constructor(
    private val transferRepository: TransferRepository,
) : GetCardOwnerByPanUseCase {
    override fun invoke(pan: String): Flow<Result<GetCardOwnerByPanData>> = flow {
        emit(transferRepository.getCardOwnerByPan(GetCardOwnerByPanRequest(pan = pan)))
    }.catch { emit(Result.failure(it)) }
}