package uz.gita.latizx.entity.repository

import uz.gita.latizx.entity.retrofit.request.auth.ResendRequest
import uz.gita.latizx.entity.retrofit.request.auth.SignInRequest
import uz.gita.latizx.entity.retrofit.request.auth.SignInVerifyRequest
import uz.gita.latizx.entity.retrofit.request.auth.SignUpRequest
import uz.gita.latizx.entity.retrofit.request.auth.SignUpVerifyRequest

interface AuthRepository {
    /*  Auth  */
    suspend fun signIn(data: SignInRequest): Result<Unit>
    suspend fun signInResend(data: ResendRequest): Result<Unit>
    suspend fun signInVerify(data: SignInVerifyRequest): Result<Unit>

    suspend fun signUp(data: SignUpRequest): Result<Unit>
    suspend fun signUpResend(data: ResendRequest): Result<Unit>
    suspend fun signUpVerify(data: SignUpVerifyRequest): Result<Unit>

    suspend fun setPinCode(pinCode: String): Result<Unit>
    suspend fun getPinCode():  Result<String>
    suspend fun getToken():  Result<String>
}