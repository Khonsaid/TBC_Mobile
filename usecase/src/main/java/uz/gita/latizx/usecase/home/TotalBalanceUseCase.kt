package uz.gita.latizx.usecase.home

import kotlinx.coroutines.flow.Flow
import uz.gita.latizx.comman.model.home.TotalBalanceData

interface TotalBalanceUseCase {
    fun invoke(): Flow<Result<TotalBalanceData>>
}