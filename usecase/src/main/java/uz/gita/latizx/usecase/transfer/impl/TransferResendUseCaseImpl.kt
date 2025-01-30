package uz.gita.latizx.usecase.transfer.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import uz.gita.latizx.comman.model.TransferVerifyData
import uz.gita.latizx.entity.local.pref.PreferenceHelper
import uz.gita.latizx.entity.repository.TransferRepository
import uz.gita.latizx.entity.retrofit.request.transfer.TransferResendRequest
import uz.gita.latizx.usecase.transfer.TransferResendUseCase
import uz.gita.latizx.usecase.transfer.TransferUseCase
import javax.inject.Inject

class TransferResendUseCaseImpl @Inject constructor(
    private val transferRepository: TransferRepository,
    private val transferUseCase: TransferUseCase,
    private val pref: PreferenceHelper,
) : TransferResendUseCase {
    override fun invoke(transferVerifyData: TransferVerifyData): Flow<Result<Unit>> = flow {
        transferRepository.transferResent(TransferResendRequest(pref.token)).onSuccess {
            transferUseCase.invoke(
                amount = transferVerifyData.amount,
                receiverPan = transferVerifyData.receiverPan,
                senderId = transferVerifyData.senderId,
                type = transferVerifyData.type
            ).onEach { result ->
                result.onSuccess {
                    emit(Result.success(Unit))
                }
                result.onFailure {
                    emit(Result.failure(it))
                }
            }
        }.onFailure {
            emit(Result.failure(it))
        }.map { }
    }.catch { emit(Result.failure(it)) }
}