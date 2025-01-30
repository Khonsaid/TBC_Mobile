package uz.gita.latizx.usecase.transfer

import kotlinx.coroutines.flow.Flow
import uz.gita.latizx.comman.model.TransferVerifyData

interface TransferResendUseCase {
    fun invoke(transferVerifyData: TransferVerifyData): Flow<Result<Unit>>
}