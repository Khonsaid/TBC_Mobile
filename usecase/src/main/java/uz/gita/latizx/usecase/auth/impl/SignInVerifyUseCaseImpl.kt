package uz.gita.latizx.usecase.auth.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import uz.gita.latizx.entity.repository.AuthRepository
import uz.gita.latizx.entity.retrofit.request.auth.SignInVerifyRequest
import uz.gita.latizx.usecase.auth.SignInVerifyUseCase
import javax.inject.Inject

class SignInVerifyUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : SignInVerifyUseCase {
    override fun invoke(code: String): Flow<Result<Unit>> = flow {
        authRepository.getToken().onSuccess { token ->
            emit(authRepository.signInVerify(SignInVerifyRequest(token = token, code = code)))

        }.onFailure {
            emit(Result.failure(it))
        }
    }.catch { emit(Result.failure(it)) }
}
