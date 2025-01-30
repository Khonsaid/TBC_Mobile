package uz.gita.latizx.usecase.card

import kotlinx.coroutines.flow.Flow

interface AddCardUseCase {
    fun invoke(pan: String, expiredYear: String, expiredMonth: String, name: String): Flow<Result<Unit>>
}