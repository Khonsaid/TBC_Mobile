package uz.gita.latizx.usecase.card.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import uz.gita.latizx.entity.retrofit.request.card.AddCardRequest
import uz.gita.latizx.entity.repository.CardRepository
import uz.gita.latizx.usecase.card.AddCardUseCase
import javax.inject.Inject

class AddCardUseCaseImpl @Inject constructor(
    private val cardRepository: CardRepository,
) : AddCardUseCase {
    override fun invoke(
        pan: String,
        expiredYear: String,
        expiredMonth: String,
        name: String,
    ): Flow<Result<Unit>> = flow {
        emit(
            cardRepository.addCard(
                AddCardRequest(pan = pan, expiredYear = expiredYear, expiredMonth = expiredMonth, name = name)
            )
        )
    }.catch { emit(Result.failure(Throwable(it))) }
}