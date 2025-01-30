package uz.gita.latizx.entity.repository

import uz.gita.latizx.comman.model.CardsData
import uz.gita.latizx.entity.retrofit.request.card.AddCardRequest

interface CardRepository {
    /*   Card   */
    suspend fun addCard(data: AddCardRequest): Result<Unit>
    suspend fun getCards(): Result<List<CardsData>>

}