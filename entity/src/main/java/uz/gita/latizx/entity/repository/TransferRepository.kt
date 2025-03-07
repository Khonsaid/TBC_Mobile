package uz.gita.latizx.entity.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.gita.latizx.comman.model.GetFeeData
import uz.gita.latizx.comman.model.transfer.GetCardOwnerByPanData
import uz.gita.latizx.entity.local.room.entity.HistoryItemsEntity
import uz.gita.latizx.entity.retrofit.request.transfer.GetCardOwnerByPanRequest
import uz.gita.latizx.entity.retrofit.request.transfer.GetFeeRequest
import uz.gita.latizx.entity.retrofit.request.transfer.TransferRequest
import uz.gita.latizx.entity.retrofit.request.transfer.TransferResendRequest
import uz.gita.latizx.entity.retrofit.request.transfer.TransferVerifyRequest
import uz.gita.latizx.entity.retrofit.response.transfer.TransferResponse
import uz.gita.latizx.entity.retrofit.response.transfer.TransferVerifyResponse

interface TransferRepository {

    fun getHistory(): Flow<PagingData<HistoryItemsEntity>>
//    fun history(): Flow<Result<GetHistoryResponse>>
    suspend fun getCardOwnerByPan(getCardOwnerByPanRequest: GetCardOwnerByPanRequest): Result<GetCardOwnerByPanData>
    suspend fun getFree(getFeeRequest: GetFeeRequest): Result<GetFeeData>
    suspend fun transfer(transferRequest: TransferRequest): Result<TransferResponse>
    suspend fun transferVerify(transferVerifyRequest: TransferVerifyRequest): Result<TransferVerifyResponse>
    suspend fun transferResent(transferResendRequest: TransferResendRequest): Result<TransferVerifyResponse>
}