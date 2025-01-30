package uz.gita.latizx.usecase.transfer

import kotlinx.coroutines.flow.Flow
import uz.gita.latizx.entity.retrofit.request.transfer.TransferRequest

interface TransferUseCase {
    fun invoke(amount: Int, receiverPan: String, senderId: String, type: String): Flow<Result<Unit>>
}