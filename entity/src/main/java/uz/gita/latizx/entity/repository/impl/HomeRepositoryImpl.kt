package uz.gita.latizx.entity.repository.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import uz.gita.latizx.comman.model.ExchangeRateModel
import uz.gita.latizx.comman.model.home.BasicInfoData
import uz.gita.latizx.comman.model.home.FullInfoData
import uz.gita.latizx.comman.model.home.LastTransferData
import uz.gita.latizx.comman.model.home.TotalBalanceData
import uz.gita.latizx.entity.mapper.toData
import uz.gita.latizx.entity.mapper.toExchangeRateModel
import uz.gita.latizx.entity.repository.HomeRepository
import uz.gita.latizx.entity.retrofit.api.ExchangeRateApi
import uz.gita.latizx.entity.retrofit.api.HomeAPI
import uz.gita.latizx.entity.utils.handleResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val exchangeRateApi: ExchangeRateApi,
    private val homeAPI: HomeAPI,
) : HomeRepository {
    override suspend fun getTotalBalance(): Result<TotalBalanceData> = withContext(Dispatchers.IO) {
        handleResponse(homeAPI.getTotalBalance()).map { it.toData() }
    }

    override suspend fun getBasicInfo(): Result<BasicInfoData> = withContext(Dispatchers.IO) {
        handleResponse(homeAPI.getBasicInfo()).map { it.toData() }
    }

    override suspend fun getFullInfo(): Result<FullInfoData> = withContext(Dispatchers.IO) {
        handleResponse(homeAPI.getFullInfo()).map { it.toData() }
    }

    override suspend fun getLastTransfer(): Result<LastTransferData> = withContext(Dispatchers.IO) {
        handleResponse(homeAPI.getLastTransfer()).map { it.toData() }
    }

    override fun getExchangeRateModel(): Flow<Result<List<ExchangeRateModel>>>  = flow {
        val result = exchangeRateApi.getExchangeRateModel()
        if (result.isSuccessful && result.body() != null) {
            var list = emptyList<ExchangeRateModel>()
            list = result.body()!!.map { it.toExchangeRateModel() }
            emit(Result.success(list))
        } else if (result.errorBody() != null) emit(Result.failure(Throwable(result.errorBody()?.string())))

    }.flowOn(Dispatchers.IO).catch { emit(Result.failure(it)) }
}