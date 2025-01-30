package uz.gita.latizx.usecase.transfer.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import uz.gita.latizx.entity.retrofit.request.transfer.TransferRequest
import uz.gita.latizx.entity.repository.TransferRepository
import uz.gita.latizx.usecase.transfer.TransferUseCase
import javax.inject.Inject

class TransferUseCaseImpl @Inject constructor(
    private val transferRepository: TransferRepository,
) : TransferUseCase {
    override fun invoke(amount: Int, receiverPan: String, senderId: String, type: String): Flow<Result<Unit>> = flow {
        emit(transferRepository.transfer(
            TransferRequest(
                amount = amount,
                receiverPan = receiverPan,
                senderId = senderId,
                type = "third-card"
            )
        ).map { })
    }.catch { emit(Result.failure(it)) }
}