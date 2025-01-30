package uz.gita.latizx.usecase.auth.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import uz.gita.latizx.entity.repository.AuthRepository
import uz.gita.latizx.entity.retrofit.request.auth.SignUpVerifyRequest
import uz.gita.latizx.usecase.auth.SignUpVerifyUseCase
import javax.inject.Inject

class SignUpVerifyUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : SignUpVerifyUseCase {
    override fun invoke(code: String): Flow<Result<Unit>> = flow {
        authRepository.getToken().onSuccess { token ->
            emit(authRepository.signUpVerify(SignUpVerifyRequest(code = code, token = token)))
        }.onFailure { emit(Result.failure(it)) }
    }.catch { emit(Result.failure(it)) }
}