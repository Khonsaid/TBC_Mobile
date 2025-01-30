package uz.gita.latizx.usecase.auth.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import uz.gita.latizx.entity.repository.AuthRepository
import uz.gita.latizx.entity.retrofit.request.auth.ResendRequest
import uz.gita.latizx.usecase.auth.SignUpResendUseCase
import javax.inject.Inject

class SignUpResendUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : SignUpResendUseCase {
    override fun invoke(): Flow<Result<Unit>> = flow {
        authRepository.getToken().onSuccess { token ->
            emit(authRepository.signUpResend(ResendRequest(token)))
        }.onFailure {
            emit(Result.failure(it))
        }
    }.catch { emit(Result.failure(it)) }
}