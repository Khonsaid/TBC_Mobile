package uz.gita.latizx.presenter.settings.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.usecase.settings.SettingsUseCase
import javax.inject.Inject

@HiltViewModel
class MapViewModelImpl @Inject constructor(
    private val directions: MapContract.Directions,
    private val settingsUseCase: SettingsUseCase,
) : MapContract.MapViewModel, ViewModel() {
    override val uiState = MutableStateFlow<MapContract.UIState>(MapContract.UIState())

    init {
        getTBCLocation()
    }

    private fun getTBCLocation() {
        val locations = settingsUseCase.getLocation()
        reduce { it.copy(data = locations) }
    }

    override fun onEventDispatcher(uiIntent: MapContract.UIIntent) {
        when (uiIntent) {
            is MapContract.UIIntent.OpenPrev -> viewModelScope.launch { directions.navigateToBack() }
            is MapContract.UIIntent.HideDialog -> reduce { it.copy(showInfoDialog = false, locationData = null) }
            is MapContract.UIIntent.ShowDialog -> reduce { it.copy(showInfoDialog = true, locationData = uiIntent.data) }
        }
    }

    private inline fun reduce(block: (MapContract.UIState) -> MapContract.UIState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }
}
