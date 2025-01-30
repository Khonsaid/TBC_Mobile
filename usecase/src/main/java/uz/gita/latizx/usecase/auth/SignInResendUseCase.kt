package uz.gita.latizx.usecase.auth

import kotlinx.coroutines.flow.Flow

interface SignInResendUseCase {
    fun invoke(password: String, phoneNumber: String): Flow<Result<Unit>>
}