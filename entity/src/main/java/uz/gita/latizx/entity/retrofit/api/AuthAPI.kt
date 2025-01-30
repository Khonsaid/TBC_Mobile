package uz.gita.latizx.entity.retrofit.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.gita.latizx.entity.retrofit.request.auth.ResendRequest
import uz.gita.latizx.entity.retrofit.request.auth.SignInRequest
import uz.gita.latizx.entity.retrofit.request.auth.SignInVerifyRequest
import uz.gita.latizx.entity.retrofit.request.auth.SignUpRequest
import uz.gita.latizx.entity.retrofit.request.auth.SignUpVerifyRequest
import uz.gita.latizx.entity.retrofit.request.auth.UpdateTokenRequest
import uz.gita.latizx.entity.retrofit.response.auth.SignInResponse
import uz.gita.latizx.entity.retrofit.response.auth.SignInVerifyResponse
import uz.gita.latizx.entity.retrofit.response.auth.SignUpResponse
import uz.gita.latizx.entity.retrofit.response.auth.SignUpVerifyResponse
import uz.gita.latizx.entity.retrofit.response.auth.UpdateTokenResponse

interface AuthAPI {
    @POST("auth/sign-in")
    suspend fun signIn(@Body data: SignInRequest): Response<SignInResponse>

    @POST("auth/sign-up")
    suspend fun signUp(@Body data: SignUpRequest): Response<SignUpResponse>

    @POST("auth/sign-up/verify")
    suspend fun signUpVerify(@Body data: SignUpVerifyRequest): Response<SignUpVerifyResponse>

    @POST("auth/sign-in/verify")
    suspend fun signInVerify(@Body data: SignInVerifyRequest): Response<SignInVerifyResponse>

    @POST("auth/sign-in/resend")
    suspend fun signInResend(@Body data: ResendRequest): Response<SignInResponse>

    @POST("auth/sign-up/resend")
    suspend fun signUpResend(@Body data: ResendRequest): Response<SignUpResponse>

    @POST("auth/update-token")
    suspend fun updateRefreshToken(@Body data: UpdateTokenRequest): Response<UpdateTokenResponse>
}