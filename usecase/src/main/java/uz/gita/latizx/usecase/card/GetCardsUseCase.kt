package uz.gita.latizx.usecase.card

import kotlinx.coroutines.flow.Flow
import uz.gita.latizx.comman.model.CardsData

interface GetCardsUseCase {
    fun invoke(): Flow<Result<List<CardsData>>>
}