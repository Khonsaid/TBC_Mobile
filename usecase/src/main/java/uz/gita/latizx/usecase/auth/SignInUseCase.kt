package uz.gita.latizx.usecase.auth

import kotlinx.coroutines.flow.Flow

interface SignInUseCase {
    fun invoke(phone: String, password: String): Flow<Result<Unit>>
}