package uz.gita.latizx.usecase.exchange_rate

import kotlinx.coroutines.flow.Flow
import uz.gita.latizx.comman.model.ExchangeRateModel
import uz.gita.latizx.entity.repository.HomeRepository
import javax.inject.Inject

class ExchangeRateUseCaseImpl @Inject constructor(
    private val homeRepositoryImpl: HomeRepository,
) : ExchangeRateUseCase {
    override fun invoke(): Flow<Result<List<ExchangeRateModel>>> = homeRepositoryImpl.getExchangeRateModel()
}