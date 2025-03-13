package uz.gita.latizx.presenter.support.support

interface SupportContract {

    sealed interface UIIntent {
        data object OpenPrev : UIIntent
        data object OpenChat : UIIntent
    }

    interface SupportViewModel{
        fun onEventDispatcher(uiIntent: UIIntent)
    }

    interface Directions{
        suspend fun navigateToBack()
        suspend fun navigateToChat()
    }
}