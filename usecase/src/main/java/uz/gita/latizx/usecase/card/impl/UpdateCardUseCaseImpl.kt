package uz.gita.latizx.usecase.card.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import uz.gita.latizx.entity.repository.CardRepository
import uz.gita.latizx.entity.retrofit.request.card.UpdateCardRequest
import uz.gita.latizx.usecase.card.UpdateCardUseCase
import javax.inject.Inject

class UpdateCardUseCaseImpl @Inject constructor(
    private val cardRepository: CardRepository,
) : UpdateCardUseCase {
    override fun invoke(id: Int, name: String, themeType: Int, isVisible: String): Flow<Result<Unit>> = flow {
        emit(
            cardRepository.updateCard(
                UpdateCardRequest(
                    id = id, name = name, themeType = themeType, isVisible = isVisible
                )
            )
        )
    }.catch { emit(Result.failure(it)) }
}