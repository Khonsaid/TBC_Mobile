package uz.gita.latizx.entity.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.latizx.comman.model.ExchangeRateModel
import uz.gita.latizx.comman.model.home.BasicInfoData
import uz.gita.latizx.comman.model.home.FullInfoData
import uz.gita.latizx.comman.model.home.LastTransferData
import uz.gita.latizx.comman.model.home.TotalBalanceData

interface HomeRepository {
    suspend fun getTotalBalance(): Result<TotalBalanceData>
    suspend fun getBasicInfo(): Result<BasicInfoData>
    suspend fun getFullInfo(): Result<FullInfoData>
    suspend fun getLastTransfer(): Result<LastTransferData>
    fun getExchangeRateModel(): Flow<Result<List<ExchangeRateModel>>>
}