package uz.gita.latizx.usecase.transfer

import kotlinx.coroutines.flow.Flow

interface TransferVerifyUseCase {
    fun invoke(code: String): Flow<Result<Unit>>
}