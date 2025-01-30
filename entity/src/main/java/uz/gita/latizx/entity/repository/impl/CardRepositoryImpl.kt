package uz.gita.latizx.entity.repository.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.gita.latizx.comman.model.CardsData
import uz.gita.latizx.entity.mapper.toData
import uz.gita.latizx.entity.repository.CardRepository
import uz.gita.latizx.entity.retrofit.api.CardAPI
import uz.gita.latizx.entity.retrofit.request.card.AddCardRequest
import uz.gita.latizx.entity.utils.handleResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardRepositoryImpl @Inject constructor(
    private val cardAPI: CardAPI,
) : CardRepository {
    override suspend fun addCard(data: AddCardRequest): Result<Unit> = withContext(Dispatchers.IO) {
        handleResponse(cardAPI.addCard(data)).map { }
    }

    override suspend fun getCards(): Result<List<CardsData>> = withContext(Dispatchers.IO) {
        handleResponse(cardAPI.getCards()).map { response ->
            response.map { it.toData() }
        }
    }
}