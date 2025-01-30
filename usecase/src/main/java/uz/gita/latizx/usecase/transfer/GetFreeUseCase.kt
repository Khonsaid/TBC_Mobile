package uz.gita.latizx.usecase.transfer

import kotlinx.coroutines.flow.Flow
import uz.gita.latizx.comman.model.GetFeeData

interface GetFreeUseCase {
    fun invoke(amount: Int, receiver: String, senderId: String):Flow<Result<GetFeeData>>
}