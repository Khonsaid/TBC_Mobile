package uz.gita.latizx.entity.repository.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.gita.latizx.comman.model.home.BasicInfoData
import uz.gita.latizx.comman.model.home.FullInfoData
import uz.gita.latizx.comman.model.home.LastTransferData
import uz.gita.latizx.comman.model.home.TotalBalanceData
import uz.gita.latizx.entity.mapper.toData
import uz.gita.latizx.entity.repository.HomeRepository
import uz.gita.latizx.entity.retrofit.api.HomeAPI
import uz.gita.latizx.entity.utils.handleResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
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
}