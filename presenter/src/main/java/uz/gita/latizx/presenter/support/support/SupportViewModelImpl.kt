package uz.gita.latizx.presenter.support.support

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SupportViewModelImpl @Inject constructor(
    private val directions: SupportContract.Directions,
) : SupportContract.SupportViewModel, ViewModel() {

    override fun onEventDispatcher(uiIntent: SupportContract.UIIntent) {
        when (uiIntent) {
            is SupportContract.UIIntent.OpenChat -> {}
            is SupportContract.UIIntent.OpenPrev -> viewModelScope.launch { directions.navigateToBack() }
        }
    }
}