package uz.gita.latizx.entity.di

import android.content.Context
import android.net.ConnectivityManager
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.latizx.entity.BuildConfig
import uz.gita.latizx.entity.retrofit.api.AuthAPI
import uz.gita.latizx.entity.retrofit.api.CardAPI
import uz.gita.latizx.entity.retrofit.api.HomeAPI
import uz.gita.latizx.entity.retrofit.api.TransferAPI
import uz.gita.latizx.entity.utils.CacheControlInterceptor
import uz.gita.latizx.entity.utils.TokenAuthenticator
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @[Provides Singleton]
    fun provideChuckerIntercepter(@ApplicationContext context: Context): ChuckerInterceptor = ChuckerInterceptor(context)

    @[Provides Singleton]
    fun provideCacheControlInterceptor(@ApplicationContext context: Context): CacheControlInterceptor = CacheControlInterceptor(context)

    @[Provides Singleton]
    fun provideCache(@ApplicationContext context: Context): Cache {
        val size = 5 * 1024 * 1024
        return Cache(context.cacheDir, size.toLong())
    }

    @[Provides Singleton AuthOkHttp]
    fun provideOkhttpForAuth(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(context))
            .build()

    @[Provides Singleton CardOkHttp]
    fun provideOkhttpForCard(
        @ApplicationContext context: Context,
        chuckerInterceptor: ChuckerInterceptor,
        tokenAuthenticator: TokenAuthenticator,
        cache: Cache,
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor { chain ->
            var request = chain.request()
            request = if (hasNetwork(context)) {
                request.newBuilder()
                    .header("Cache-Control", "public, max-age=60") // 1 daqiqa
                    .build()
            } else {
                request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=86400") // 1 kun
                    .build()
            }
            chain.proceed(request)
        }
        .addInterceptor(chuckerInterceptor)
        .authenticator(tokenAuthenticator)
        .build()

    @[Provides Singleton HomeOkHttp]
    fun provideOkhttpForHome(
        @ApplicationContext context: Context,
        chuckerInterceptor: ChuckerInterceptor,
        tokenAuthenticator: TokenAuthenticator,
        cache: Cache,
        ): OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor { chain ->
            var request = chain.request()
            request = if (hasNetwork(context)) {
                request.newBuilder()
                    .header("Cache-Control", "public, max-age=60") // 1 daqiqa
                    .build()
            } else {
                request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=86400") // 1 kun
                    .build()
            }
            chain.proceed(request)
        }
        .addInterceptor(chuckerInterceptor)
        .authenticator(tokenAuthenticator)
        .build()

    @[Provides Singleton TransferOkHttp]
    fun provideOkhttpForTransfer(
        @ApplicationContext context: Context,
        chuckerInterceptor: ChuckerInterceptor,
        tokenAuthenticator: TokenAuthenticator,
        cache: Cache,
        provideCacheControlInterceptor: CacheControlInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor { chain ->
            var request = chain.request()
            request = if (hasNetwork(context)) {
                request.newBuilder()
                    .header("Cache-Control", "public, max-age=60") // 1 daqiqa
                    .build()
            } else {
                request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=86400") // 1 kun
                    .build()
            }
            chain.proceed(request)
        }
        .addInterceptor(provideCacheControlInterceptor)
        .addInterceptor(chuckerInterceptor)
        .authenticator(tokenAuthenticator)
        .build()

    @[Provides Singleton]
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @[Provides Singleton]
    fun provideAuthApi(
        @AuthOkHttp okHttpClient: OkHttpClient,
        retrofit: Retrofit,
    ): AuthAPI = retrofit.newBuilder()
        .client(okHttpClient)
        .build()
        .create(AuthAPI::class.java)

    @[Provides Singleton]
    fun provideCardApi(
        @CardOkHttp okHttpClient: OkHttpClient,
        retrofit: Retrofit,
    ): CardAPI = retrofit.newBuilder()
        .client(okHttpClient)
        .build()
        .create(CardAPI::class.java)

    @[Provides Singleton]
    fun provideHomeApi(
        @HomeOkHttp okHttpClient: OkHttpClient,
        retrofit: Retrofit,
    ): HomeAPI = retrofit.newBuilder()
        .client(okHttpClient)
        .build()
        .create(HomeAPI::class.java)

    @[Provides Singleton]
    fun provideTransferAPI(
        @TransferOkHttp okHttpClient: OkHttpClient,
        retrofit: Retrofit,
    ): TransferAPI = retrofit.newBuilder()
        .client(okHttpClient)
        .build()
        .create(TransferAPI::class.java)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthOkHttp

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CardOkHttp

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HomeOkHttp

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TransferOkHttp


private fun hasNetwork(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_WIFI) ||
            capabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_CELLULAR)
}
