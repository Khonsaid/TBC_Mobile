package uz.gita.latizx.usecase.settings.impl

import uz.gita.latizx.comman.ThemeMode
import uz.gita.latizx.entity.local.pref.PreferenceHelper
import uz.gita.latizx.usecase.settings.SettingsUseCase
import javax.inject.Inject

class SettingsUseCaseImpl @Inject constructor(
    private val pref: PreferenceHelper,
) : SettingsUseCase {
    override fun isBalanceDisplayed(): Boolean = pref.isBalanceDisplayed

    override fun changeBalanceDisplayed(): Boolean {
        pref.isBalanceDisplayed = !pref.isBalanceDisplayed
        return pref.isBalanceDisplayed
    }

    override fun getCurrTheme(): String = pref.theme

    override fun setCurrTheme(theme: ThemeMode) {
        pref.theme = theme.value
    }

    override fun getBiometricStatus(): Boolean = pref.statusBiometric

    override fun changeBiometricStatus() {
        pref.statusBiometric = !pref.statusBiometric
    }

    override fun logOut() {
        pref.pinCode = ""
    }
}