package uz.gita.latizx.entity.retrofit.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import uz.gita.latizx.entity.retrofit.request.card.AddCardRequest
import uz.gita.latizx.entity.retrofit.response.SuccessResponse
import uz.gita.latizx.entity.retrofit.response.card.CardsResponse

interface CardAPI {

    @GET("card")
    suspend fun getCards(): Response<List<CardsResponse>>

    @POST("card")
    suspend fun addCard(@Body data: AddCardRequest): Response<SuccessResponse>

}
