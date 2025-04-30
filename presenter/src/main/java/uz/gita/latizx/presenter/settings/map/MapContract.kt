package uz.gita.latizx.presenter.settings.map

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import uz.gita.latizx.comman.model.map.LocationData

interface MapContract {
    sealed interface UIIntent {
        data object OpenPrev : UIIntent
        data class ShowDialog(val data : LocationData) : UIIntent
        data object HideDialog : UIIntent
    }

    data class UIState(
        val data: List<LocationData> = emptyList(),
        val location: Pair<Double, Double> = Pair(41.314514, 69.237720),
        val showInfoDialog: Boolean = false,
        val locationData: LocationData? = null,
    )


    interface MapViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(uiIntent: UIIntent)
    }

    interface Directions {
        suspend fun navigateToBack()
    }
}