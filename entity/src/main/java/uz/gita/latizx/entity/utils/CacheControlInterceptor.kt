package uz.gita.latizx.entity.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class CacheControlInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // Tarmoq mavjudligini tekshirib olish
        val isNetworkAvailable = isNetworkAvailable(context)

        // CacheControl sarlavhasini aniqlash
        val cacheControl = if (isNetworkAvailable) {
            CacheControl.FORCE_NETWORK // Tarmoq bor bo'lsa, yangi ma'lumot olish
        } else {
            CacheControl.FORCE_CACHE // Tarmoq yo'q bo'lsa, keshni ishlatish
        }

        // So'rovni o'zgartirish va Cache-Control sarlavhasini qo'shish
        val request = chain.request().newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()

        // So'rovni yuborish va javobni qaytarish
        return chain.proceed(request)
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}