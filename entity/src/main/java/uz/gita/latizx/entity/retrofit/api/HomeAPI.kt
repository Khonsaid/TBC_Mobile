package uz.gita.latizx.entity.retrofit.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import uz.gita.latizx.entity.retrofit.request.home.UpdateInfoRequest
import uz.gita.latizx.entity.retrofit.response.SuccessResponse
import uz.gita.latizx.entity.retrofit.response.home.BasicInfoResponse
import uz.gita.latizx.entity.retrofit.response.home.FullInfoResponse
import uz.gita.latizx.entity.retrofit.response.home.LastTransferResponse
import uz.gita.latizx.entity.retrofit.response.home.TotalBalanceResponse

interface HomeAPI {

    @GET("home/total-balance")
    suspend fun getTotalBalance(): Response<TotalBalanceResponse>

    @GET("home/user-info")
    suspend fun getBasicInfo(): Response<BasicInfoResponse>

    @GET("user-info/details")
    suspend fun getFullInfo(): Response<FullInfoResponse>

    @GET("home/last-transfers")
    suspend fun getLastTransfer(): Response<LastTransferResponse>

    @PUT("home/user-info")
    suspend fun updateInfo(@Body data: UpdateInfoRequest): Response<SuccessResponse>
}