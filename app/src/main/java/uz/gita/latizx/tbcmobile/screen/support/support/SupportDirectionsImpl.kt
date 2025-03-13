package uz.gita.latizx.tbcmobile.screen.support.support

import uz.gita.latizx.presenter.support.support.SupportContract
import uz.gita.latizx.tbcmobile.navigator.AppNavigator
import uz.gita.latizx.tbcmobile.screen.support.chat.ChatScreen
import javax.inject.Inject

class SupportDirectionsImpl @Inject constructor(
    private val appNavigator: AppNavigator,
) : SupportContract.Directions {
    override suspend fun navigateToBack() {
        appNavigator.back()
    }

    override suspend fun navigateToChat() {
        appNavigator.navigateTo(ChatScreen())
    }
}