package uz.gita.latizx.usecase.home.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import uz.gita.latizx.comman.model.home.TotalBalanceData
import uz.gita.latizx.entity.repository.HomeRepository
import uz.gita.latizx.usecase.home.TotalBalanceUseCase
import javax.inject.Inject

class TotalBalanceUseCaseImpl @Inject constructor(
    private val homeRepositoryImpl: HomeRepository,
) : TotalBalanceUseCase {
    override fun invoke(): Flow<Result<TotalBalanceData>> = flow {
        emit(homeRepositoryImpl.getTotalBalance())
    }.catch { emit(Result.failure(it)) }
}