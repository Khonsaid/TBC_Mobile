package uz.gita.latizx.presenter.other.success

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.gita.latizx.comman.model.RecipientData
import uz.gita.latizx.presenter.success.SuccessContract

@HiltViewModel(assistedFactory = SuccessViewModelImpl.Factory::class)
class SuccessViewModelImpl @AssistedInject constructor(
    @Assisted private val recipientData: RecipientData,
    private val directions: SuccessContract.Directions,
) : SuccessContract.SuccessViewModel, ViewModel() {
    @AssistedFactory
    interface Factory {
        fun onCreate(recipientData: RecipientData): SuccessViewModelImpl
    }

    override val uiState = MutableStateFlow<SuccessContract.UIState>(
        SuccessContract.UIState(
            recipientName = recipientData.recipientName,
            recipientPan = recipientData.recipientPan
        )
    )

    override fun onEventDispatcher(intent: SuccessContract.SuccessIntent) {
        when (intent) {
            SuccessContract.SuccessIntent.OpenMoneyTransfersScreen -> viewModelScope.launch { directions.navigateToMoneyTransfers() }
        }
    }
}