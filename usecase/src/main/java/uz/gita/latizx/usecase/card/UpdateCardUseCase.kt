package uz.gita.latizx.usecase.card

import kotlinx.coroutines.flow.Flow

interface UpdateCardUseCase {
    fun invoke(id: Int, name: String, themeType: Int, isVisible: String): Flow<Result<Unit>>
}