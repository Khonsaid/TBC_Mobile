package uz.gita.latizx.usecase.auth.impl

import uz.gita.latizx.entity.repository.AuthRepository
import uz.gita.latizx.usecase.auth.PinCodeUseCase
import javax.inject.Inject

class PinCodeUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : PinCodeUseCase {
    override suspend fun setPinCode(pinCode: String): Result<Unit> = authRepository.setPinCode(pinCode = pinCode)

    override suspend fun getPinCode(): Result<String> = authRepository.getPinCode()
}