package uz.gita.latizx.usecase.auth

import kotlinx.coroutines.flow.Flow

interface SignInVerifyUseCase {
    fun invoke(code: String): Flow<Result<Unit>>
}