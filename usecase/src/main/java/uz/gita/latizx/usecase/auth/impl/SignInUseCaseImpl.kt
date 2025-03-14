package uz.gita.latizx.usecase.auth.impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import uz.gita.latizx.entity.repository.AuthRepository
import uz.gita.latizx.entity.retrofit.request.auth.SignInRequest
import uz.gita.latizx.usecase.auth.SignInUseCase
import javax.inject.Inject

class SignInUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : SignInUseCase {
    override fun invoke(phone: String, password: String): Flow<Result<Unit>> = flow {
        emit(authRepository.signIn(SignInRequest(phone = phone, password = password)))
    }.catch { emit(Result.failure(it)) }
}