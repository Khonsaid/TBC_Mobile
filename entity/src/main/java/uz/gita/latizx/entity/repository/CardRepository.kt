package uz.gita.latizx.entity.repository

import uz.gita.latizx.comman.model.CardsData
import uz.gita.latizx.entity.retrofit.request.card.AddCardRequest
import uz.gita.latizx.entity.retrofit.request.card.UpdateCardRequest

interface CardRepository {
    /*   Card   */
    suspend fun addCard(data: AddCardRequest): Result<Unit>
    suspend fun getCards(): Result<List<CardsData>>
    suspend fun updateCard(data: UpdateCardRequest): Result<Unit>
    suspend fun deleteCardById(id: Int): Result<Unit>
}