package uz.gita.latizx.usecase.home

import kotlinx.coroutines.flow.Flow

interface LastTransferUseCase {
    fun invoke():Flow<Result<Unit>>
}