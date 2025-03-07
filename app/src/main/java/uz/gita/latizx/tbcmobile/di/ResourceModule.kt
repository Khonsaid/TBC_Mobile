package uz.gita.latizx.tbcmobile.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.latizx.presenter.utils.ResourceManager
import uz.gita.latizx.presenter.utils.ResourceManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ResourceModule {
    @Provides
    @Singleton
    fun provideResourceManager(@ApplicationContext context: Context): ResourceManager {
        return ResourceManagerImpl(context)
    }
}