package uz.gita.latizx.entity.repository.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.gita.latizx.entity.local.pref.PreferenceHelper
import uz.gita.latizx.entity.repository.AuthRepository
import uz.gita.latizx.entity.retrofit.api.AuthAPI
import uz.gita.latizx.entity.retrofit.request.auth.ResendRequest
import uz.gita.latizx.entity.retrofit.request.auth.SignInRequest
import uz.gita.latizx.entity.retrofit.request.auth.SignInVerifyRequest
import uz.gita.latizx.entity.retrofit.request.auth.SignUpRequest
import uz.gita.latizx.entity.retrofit.request.auth.SignUpVerifyRequest
import uz.gita.latizx.entity.utils.handleResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authAPI: AuthAPI,
    private val pref: PreferenceHelper,
) : AuthRepository {

    override suspend fun signIn(data: SignInRequest): Result<Unit> = withContext(Dispatchers.IO) {
        (handleResponse(authAPI.signIn(data)) {
            pref.token = it.token
        }).map { Unit }
    }

    override suspend fun signInResend(data: ResendRequest): Result<Unit> = withContext(Dispatchers.IO) {
        handleResponse(authAPI.signInResend(data)) { pref.token = it.token }.map { Unit }
    }

    override suspend fun signInVerify(data: SignInVerifyRequest): Result<Unit> = withContext(Dispatchers.IO) {
        handleResponse(authAPI.signInVerify(data)) {
            pref.accessToken = it.accessToken
            pref.refreshToken = it.refreshToken
        }.map { Unit }
    }

    override suspend fun signUp(data: SignUpRequest): Result<Unit> = withContext(Dispatchers.IO) {
        handleResponse(authAPI.signUp(data)) { pref.token = it.token }.map { Unit }
    }

    override suspend fun signUpResend(data: ResendRequest): Result<Unit> = withContext(Dispatchers.IO) {
        handleResponse(authAPI.signUpResend(data)) { pref.token = it.token }.map { Unit }
    }

    override suspend fun signUpVerify(data: SignUpVerifyRequest): Result<Unit> = withContext(Dispatchers.IO) {
        handleResponse(authAPI.signUpVerify(data)) {
            pref.accessToken = it.accessToken
            pref.refreshToken = it.refreshToken
        }.map { Unit }
    }

    override suspend fun setPinCode(pinCode: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            pref.pinCode = pinCode
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPinCode(): Result<String> = withContext(Dispatchers.IO) {
        try {
            Result.success(pref.pinCode)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getToken(): Result<String> = withContext(Dispatchers.IO) {
        try {
            Result.success(pref.token)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}