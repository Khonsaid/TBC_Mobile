package uz.gita.latizx.usecase.home

import kotlinx.coroutines.flow.Flow

interface FullInfoUseCase {
    fun invoke():Flow<Result<Unit>>
}