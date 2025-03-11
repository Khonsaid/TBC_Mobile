package uz.gita.latizx.usecase.exchange_rate

import kotlinx.coroutines.flow.Flow
import uz.gita.latizx.comman.model.ExchangeRateModel
import uz.gita.latizx.comman.model.home.TotalBalanceData

interface ExchangeRateUseCase {
    fun invoke(): Flow<Result<List<ExchangeRateModel>>>
}