package uz.gita.latizx.entity.retrofit.request.auth

data class SignUpVerifyRequest(
    val token: String,
    val code: String
)
