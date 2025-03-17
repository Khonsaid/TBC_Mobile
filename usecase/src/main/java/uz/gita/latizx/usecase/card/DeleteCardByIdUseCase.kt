package uz.gita.latizx.usecase.card

import kotlinx.coroutines.flow.Flow

interface DeleteCardByIdUseCase {
    fun invoke(id: Int): Flow<Result<Unit>>
}