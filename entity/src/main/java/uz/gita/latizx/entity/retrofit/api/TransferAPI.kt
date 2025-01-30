package uz.gita.latizx.entity.retrofit.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import uz.gita.latizx.comman.model.GetHistoryResponse
import uz.gita.latizx.entity.retrofit.request.transfer.GetCardOwnerByPanRequest
import uz.gita.latizx.entity.retrofit.request.transfer.GetFeeRequest
import uz.gita.latizx.entity.retrofit.request.transfer.TransferRequest
import uz.gita.latizx.entity.retrofit.request.transfer.TransferResendRequest
import uz.gita.latizx.entity.retrofit.request.transfer.TransferVerifyRequest
import uz.gita.latizx.entity.retrofit.response.transfer.GetCardOwnerByPanResponse
import uz.gita.latizx.entity.retrofit.response.transfer.GetFeeResponse
import uz.gita.latizx.entity.retrofit.response.transfer.TransferResponse
import uz.gita.latizx.entity.retrofit.response.transfer.TransferVerifyResponse

interface TransferAPI {
    @POST("transfer/card-owner")
    suspend fun getCardOwnerByPan(@Body getCardOwnerByPanRequest: GetCardOwnerByPanRequest): Response<GetCardOwnerByPanResponse>

    @POST("transfer/fee")
    suspend fun getFree(@Body getFeeRequest: GetFeeRequest): Response<GetFeeResponse>

    @POST("transfer/transfer")
    suspend fun transfer(@Body transferRequest: TransferRequest): Response<TransferResponse>

    @POST("transfer/transfer/verify")
    suspend fun transferVerify(@Body transferVerifyRequest: TransferVerifyRequest): Response<TransferVerifyResponse>

    @POST("transfer/transfer/resend")
    suspend fun transferResent(@Body transferResendRequest: TransferResendRequest): Response<TransferVerifyResponse>

    @GET("transfer/history")
    suspend fun getHistory(
        @Query("size") size: Int,
        @Query("current-page") currentPage: Int,
    ): Response<GetHistoryResponse>

//    @GET("transfer/history?size=4&current-page=1")
//    suspend fun getHistory(): Response<GetHistoryResponse>
}