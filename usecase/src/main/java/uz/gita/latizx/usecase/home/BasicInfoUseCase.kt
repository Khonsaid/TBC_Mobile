package uz.gita.latizx.usecase.home

import kotlinx.coroutines.flow.Flow

interface BasicInfoUseCase {
    fun invoke():Flow<Result<Unit>>
}