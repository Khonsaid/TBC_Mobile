package uz.gita.latizx.usecase.transfer.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import uz.gita.latizx.entity.repository.AuthRepository
import uz.gita.latizx.entity.retrofit.request.transfer.TransferVerifyRequest
import uz.gita.latizx.entity.repository.TransferRepository
import uz.gita.latizx.usecase.transfer.TransferVerifyUseCase
import javax.inject.Inject

class TransferVerifyUseCaseImpl @Inject constructor(
    private val transferRepository: TransferRepository,
    private val authRepository: AuthRepository,
) : TransferVerifyUseCase {
    override fun invoke(code: String): Flow<Result<Unit>> = flow {
        authRepository.getToken().onSuccess { token ->
            emit(transferRepository.transferVerify(TransferVerifyRequest(code = code, token = token)).map { Unit })
        }.onFailure { emit(Result.failure(it)) }
    }.catch { emit(Result.failure(it)) }
}