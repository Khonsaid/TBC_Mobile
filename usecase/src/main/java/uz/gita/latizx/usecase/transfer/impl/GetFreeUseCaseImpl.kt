package uz.gita.latizx.usecase.transfer.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import uz.gita.latizx.comman.model.GetFeeData
import uz.gita.latizx.entity.retrofit.request.transfer.GetFeeRequest
import uz.gita.latizx.entity.repository.TransferRepository
import uz.gita.latizx.usecase.transfer.GetFreeUseCase
import javax.inject.Inject

class GetFreeUseCaseImpl @Inject constructor(
    private val transferRepository: TransferRepository,
) : GetFreeUseCase {
    override fun invoke(amount: Int, receiver: String, senderId: String): Flow<Result<GetFeeData>> = flow {
        emit(transferRepository.getFree(GetFeeRequest(amount = amount, receiver = receiver, senderId = senderId)))
    }.catch { emit(Result.failure(it)) }
}