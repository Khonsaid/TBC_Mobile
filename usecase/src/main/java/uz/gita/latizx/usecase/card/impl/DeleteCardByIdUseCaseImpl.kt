package uz.gita.latizx.usecase.card.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import uz.gita.latizx.entity.repository.CardRepository
import uz.gita.latizx.usecase.card.DeleteCardByIdUseCase
import javax.inject.Inject

class DeleteCardByIdUseCaseImpl @Inject constructor(
    private val cardRepository: CardRepository,
) : DeleteCardByIdUseCase {
    override fun invoke(id: Int): Flow<Result<Unit>> = flow {
        emit(
            cardRepository.deleteCardById(id = id)
        )
    }.catch { emit(Result.failure(it)) }
}