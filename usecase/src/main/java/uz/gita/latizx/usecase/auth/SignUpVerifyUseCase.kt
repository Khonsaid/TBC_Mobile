package uz.gita.latizx.usecase.auth

import kotlinx.coroutines.flow.Flow

interface SignUpVerifyUseCase {
    fun invoke(code: String): Flow<Result<Unit>>
}