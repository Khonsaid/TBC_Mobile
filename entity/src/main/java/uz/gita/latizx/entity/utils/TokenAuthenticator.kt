package uz.gita.latizx.entity.utils

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import uz.gita.latizx.entity.local.pref.PreferenceHelper
import uz.gita.latizx.entity.retrofit.api.AuthAPI
import uz.gita.latizx.entity.retrofit.request.auth.UpdateTokenRequest
import uz.gita.latizx.entity.retrofit.response.auth.UpdateTokenResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val pref: PreferenceHelper,
    private val authApi: AuthAPI,
) : Authenticator {
    private val header = "Authorization"

    override fun authenticate(route: Route?, response: Response): Request {
        synchronized(this) {
            val sessionData = if (isRefreshNeeded(response)) {
                runBlocking { getUpdatedSessionData() }
            } else {
                getExistingToken()
            }

            return response.request.newBuilder()
                .header(header, "Bearer ${sessionData.accessToken}")
                .build()
        }
    }

    private fun isRefreshNeeded(response: Response): Boolean {
        return (response.code == 401)
    }

    private fun getExistingToken(): UpdateTokenResponse = UpdateTokenResponse(
        accessToken = pref.accessToken,
        refreshToken = pref.refreshToken
    )

    private suspend fun getUpdatedSessionData(): UpdateTokenResponse {
        val updateTokenRequest =
            authApi.updateRefreshToken(UpdateTokenRequest(refreshToken = pref.refreshToken))
        return updateTokenRequest.body()!!
    }
}