package uz.gita.latizx.usecase.auth

import kotlinx.coroutines.flow.Flow

interface SignUpResendUseCase {
    fun invoke(): Flow<Result<Unit>>
}