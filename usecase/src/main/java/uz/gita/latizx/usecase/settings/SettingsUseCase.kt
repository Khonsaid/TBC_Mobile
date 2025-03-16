package uz.gita.latizx.usecase.settings

import uz.gita.latizx.comman.ThemeMode

interface SettingsUseCase {
    fun isBalanceDisplayed(): Boolean
    fun changeBalanceDisplayed(): Boolean
    fun getCurrTheme(): String
    fun setCurrTheme(theme: ThemeMode)
    fun getBiometricStatus(): Boolean
    fun changeBiometricStatus()
    fun logOut()
}