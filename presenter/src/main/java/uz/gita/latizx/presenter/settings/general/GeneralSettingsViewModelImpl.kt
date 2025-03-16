package uz.gita.latizx.presenter.settings.general

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.comman.LocationHelper
import uz.gita.latizx.presenter.settings.general.GeneralSettingsContract.GeneralSettingsViewModel
import uz.gita.latizx.usecase.settings.SettingsUseCase
import javax.inject.Inject

@HiltViewModel
class GeneralSettingsViewModelImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val directions: GeneralSettingsContract.Directions,
    private val settingsUseCase: SettingsUseCase,
) : GeneralSettingsViewModel, ViewModel() {
    override val uiState = MutableStateFlow<GeneralSettingsContract.UIState>(GeneralSettingsContract.UIState())

    init {
        loadData()
    }

    override fun onEventDispatcher(uiIntent: GeneralSettingsContract.UIIntent) {
        when (uiIntent) {
            is GeneralSettingsContract.UIIntent.OpenPrevScreen -> viewModelScope.launch { directions.navigateToBack() }
            is GeneralSettingsContract.UIIntent.SelectLang -> {
                LocationHelper.setLocation(context, uiIntent.lang)
                reduce { it.copy(currLang = uiIntent.lang) }
            }

            is GeneralSettingsContract.UIIntent.SelectTheme -> {
                settingsUseCase.setCurrTheme(uiIntent.theme)
                reduce { it.copy(currTheme = uiIntent.theme.value) }
            }
        }
    }

    private fun loadData() {
        reduce { it.copy(currTheme = settingsUseCase.getCurrTheme(), currLang = LocationHelper.getLang()) }
    }

    private inline fun reduce(block: (GeneralSettingsContract.UIState) -> GeneralSettingsContract.UIState) {
        val old = uiState.value
        val new = block(old)
        uiState.value = new
    }
}
