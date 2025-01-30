package uz.gita.latizx.tbcmobile.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.tbcmobile.navigator.AppNavigatorDispatcher
import uz.gita.latizx.tbcmobile.navigator.AppNavigatorHandler

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    fun bindAppNavigator(impl: AppNavigatorDispatcher): AppNavigator

    @Binds
    fun bindAppNavigatorHandler(impl: AppNavigatorDispatcher): AppNavigatorHandler
}