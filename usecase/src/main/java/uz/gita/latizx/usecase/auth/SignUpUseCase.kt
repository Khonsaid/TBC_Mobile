package uz.gita.latizx.usecase.auth

import kotlinx.coroutines.flow.Flow

interface SignUpUseCase {
    fun invoke(phone: String, password: String, firstName: String, lastName: String, bornDate: String, gender: String): Flow<Result<Unit>>
}