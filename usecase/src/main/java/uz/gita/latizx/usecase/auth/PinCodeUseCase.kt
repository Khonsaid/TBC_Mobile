package uz.gita.latizx.usecase.auth

interface PinCodeUseCase {
   suspend fun setPinCode(pinCode: String): Result<Unit>
   suspend fun getPinCode(): Result<String>
}