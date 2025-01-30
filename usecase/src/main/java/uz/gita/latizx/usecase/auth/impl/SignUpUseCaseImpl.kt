package uz.gita.latizx.usecase.auth.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import uz.gita.latizx.entity.repository.AuthRepository
import uz.gita.latizx.entity.retrofit.request.auth.SignUpRequest
import uz.gita.latizx.usecase.auth.SignUpUseCase
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : SignUpUseCase {
    override fun invoke(
        phone: String,
        password: String,
        firstName: String,
        lastName: String,
        bornDate: String,
        gender: String
    ): Flow<Result<Unit>> = flow {
        emit(
            authRepository.signUp(
                SignUpRequest(
                    phone = phone,
                    password = password,
                    firstName = firstName,
                    lastName = lastName,
                    bornDate = bornDate,
                    gender = gender
                )
            )
        )
    }.catch { emit(Result.failure(it)) }
}