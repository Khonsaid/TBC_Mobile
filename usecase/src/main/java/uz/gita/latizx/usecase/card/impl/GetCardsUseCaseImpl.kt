package uz.gita.latizx.usecase.card.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import uz.gita.latizx.comman.model.CardsData
import uz.gita.latizx.entity.repository.CardRepository
import uz.gita.latizx.usecase.card.GetCardsUseCase
import javax.inject.Inject

class GetCardsUseCaseImpl @Inject constructor(
    private val cardRepository: CardRepository,
) : GetCardsUseCase {
    override fun invoke(): Flow<Result<List<CardsData>>> = flow {
        emit(cardRepository.getCards())
    }.catch { emit(Result.failure(it)) }
}