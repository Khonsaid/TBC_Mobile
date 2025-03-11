package uz.gita.latizx.entity.retrofit.api

import retrofit2.Response
import retrofit2.http.GET
import uz.gita.latizx.entity.retrofit.response.exchange_rate.ExchangeRateResponse

interface ExchangeRateApi {
    @GET("json")
    suspend fun getExchangeRateModel() : Response<List<ExchangeRateResponse>>
}