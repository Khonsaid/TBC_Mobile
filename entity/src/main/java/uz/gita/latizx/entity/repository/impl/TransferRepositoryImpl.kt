package uz.gita.latizx.entity.repository.impl

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import uz.gita.latizx.comman.model.GetFeeData
import uz.gita.latizx.comman.model.transfer.GetCardOwnerByPanData
import uz.gita.latizx.entity.local.pref.PreferenceHelper
import uz.gita.latizx.entity.local.room.AppDatabase
import uz.gita.latizx.entity.local.room.entity.HistoryItemsEntity
import uz.gita.latizx.entity.mapper.toDate
import uz.gita.latizx.entity.repository.TransferRepository
import uz.gita.latizx.entity.retrofit.api.TransferAPI
import uz.gita.latizx.entity.retrofit.paging.HistoryRemoteMediator
import uz.gita.latizx.entity.retrofit.request.transfer.GetCardOwnerByPanRequest
import uz.gita.latizx.entity.retrofit.request.transfer.GetFeeRequest
import uz.gita.latizx.entity.retrofit.request.transfer.TransferRequest
import uz.gita.latizx.entity.retrofit.request.transfer.TransferResendRequest
import uz.gita.latizx.entity.retrofit.request.transfer.TransferVerifyRequest
import uz.gita.latizx.entity.retrofit.response.transfer.TransferResponse
import uz.gita.latizx.entity.retrofit.response.transfer.TransferVerifyResponse
import uz.gita.latizx.entity.utils.handleResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransferRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val transferAPI: TransferAPI,
    private val db: AppDatabase,
    private val pref: PreferenceHelper,
) : TransferRepository {
    override suspend fun getCardOwnerByPan(getCardOwnerByPanRequest: GetCardOwnerByPanRequest): Result<GetCardOwnerByPanData> =
        withContext(Dispatchers.IO) {
            handleResponse(transferAPI.getCardOwnerByPan(getCardOwnerByPanRequest)).map { it.toDate() }
        }

    override suspend fun getFree(getFeeRequest: GetFeeRequest): Result<GetFeeData> = withContext(Dispatchers.IO) {
        handleResponse(transferAPI.getFree(getFeeRequest)).map { it.toDate() }
    }

    override suspend fun transfer(transferRequest: TransferRequest): Result<TransferResponse> = withContext(Dispatchers.IO) {
        handleResponse(transferAPI.transfer(transferRequest)) {
            pref.token = it.token
        }
    }

    override suspend fun transferVerify(transferVerifyRequest: TransferVerifyRequest): Result<TransferVerifyResponse> =
        withContext(Dispatchers.IO) {
            handleResponse(transferAPI.transferVerify(transferVerifyRequest))
        }

    override suspend fun transferResent(transferResendRequest: TransferResendRequest): Result<TransferVerifyResponse> =
        withContext(Dispatchers.IO) {
            handleResponse(transferAPI.transferResent(transferResendRequest)) {
                pref.token = it.token
            }
        }

//    override fun history(): Flow<Result<GetHistoryResponse>> = flow {
//        emit(handleResponse(transferAPI.getHistory()))
//    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getHistory(): Flow<PagingData<HistoryItemsEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 6),
            remoteMediator = HistoryRemoteMediator(transferAPI = transferAPI, db = db, context = context),
            pagingSourceFactory = { db.getHistoryItemsDao().pagingSource() }
        ).flow
    }
}