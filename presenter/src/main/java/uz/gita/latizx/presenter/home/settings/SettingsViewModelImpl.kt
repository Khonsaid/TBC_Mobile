package uz.gita.latizx.presenter.home.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import uz.gita.latizx.usecase.settings.SettingsUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModelImpl @Inject constructor(
    private val directions: SettingsContract.Directions,
    private val settingsUseCase: SettingsUseCase,
) : SettingsContract.SettingsViewModel, ViewModel() {
    override val uiState = MutableStateFlow<SettingsContract.UIState>(SettingsContract.UIState())


    override fun onEventDispatcher(uiIntent: SettingsContract.UIIntent) {

    }

}