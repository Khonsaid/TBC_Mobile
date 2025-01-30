package uz.gita.latizx.entity.retrofit.request.auth

data class SignInVerifyRequest(
    val token: String,
    val code: String
)
