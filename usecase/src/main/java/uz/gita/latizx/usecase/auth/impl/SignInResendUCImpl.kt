package uz.gita.latizx.usecase.auth.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import uz.gita.latizx.entity.repository.AuthRepository
import uz.gita.latizx.entity.retrofit.request.auth.ResendRequest
import uz.gita.latizx.usecase.auth.SignInResendUseCase
import uz.gita.latizx.usecase.auth.SignInUseCase
import javax.inject.Inject

class SignInResendUCImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val signInUseCase: SignInUseCase,
) : SignInResendUseCase {
    override fun invoke(phone: String, password: String): Flow<Result<Unit>> = flow {
        // 1. Tokenni olish
        val tokenResult = authRepository.getToken()
        if (tokenResult.isFailure) {
            emit(Result.failure(tokenResult.exceptionOrNull()!!))
            return@flow
        }
        val token = tokenResult.getOrNull()!!

        // 2. Sign-in resend chaqiruvini bajarish
        val resendResult = authRepository.signInResend(ResendRequest(token))
        if (resendResult.isFailure) {
            emit(Result.failure(resendResult.exceptionOrNull()!!))
            return@flow
        }

        // 3. Sign-inni qayta amalga oshirish
        signInUseCase.invoke(phone, password).collect { result ->
            if (result.isSuccess) {
                emit(Result.success(Unit))
            } else {
                emit(Result.failure(result.exceptionOrNull()!!))
            }
        }
    }.catch { emit(Result.failure(it)) }
}