package uz.gita.latizx.entity.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.latizx.entity.local.pref.PreferenceHelper
import uz.gita.latizx.entity.local.room.AppDatabase
import uz.gita.latizx.entity.repository.AuthRepository
import uz.gita.latizx.entity.repository.CardRepository
import uz.gita.latizx.entity.repository.HomeRepository
import uz.gita.latizx.entity.repository.TransferRepository
import uz.gita.latizx.entity.repository.impl.AuthRepositoryImpl
import uz.gita.latizx.entity.repository.impl.CardRepositoryImpl
import uz.gita.latizx.entity.repository.impl.HomeRepositoryImpl
import uz.gita.latizx.entity.repository.impl.TransferRepositoryImpl
import uz.gita.latizx.entity.retrofit.api.AuthAPI
import uz.gita.latizx.entity.retrofit.api.CardAPI
import uz.gita.latizx.entity.retrofit.api.ExchangeRateApi
import uz.gita.latizx.entity.retrofit.api.HomeAPI
import uz.gita.latizx.entity.retrofit.api.TransferAPI
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideAuthRepository(
        authAPI: AuthAPI,
        pref: PreferenceHelper,
    ): AuthRepository {
        return AuthRepositoryImpl(
            authAPI = authAPI,
            pref = pref
        )
    }

    @Provides
    @Singleton
    fun provideCardRepository(
        cardAPI: CardAPI,
    ): CardRepository {
        return CardRepositoryImpl(
            cardAPI = cardAPI
        )
    }

    @Provides
    @Singleton
    fun provideHomeRepository(
        homeAPI: HomeAPI,
        exchangeRateApi: ExchangeRateApi
    ): HomeRepository {
        return HomeRepositoryImpl(
            homeAPI = homeAPI,
            exchangeRateApi = exchangeRateApi
        )
    }

    @Provides
    @Singleton
    fun provideTransferRepository(
        @ApplicationContext context: Context,
        transferAPI: TransferAPI,
        pref: PreferenceHelper,
        db: AppDatabase,
    ): TransferRepository {
        return TransferRepositoryImpl(
            transferAPI = transferAPI,
            pref = pref,
            db = db,
            context = context
        )
    }
}
