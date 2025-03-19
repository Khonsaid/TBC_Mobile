package uz.gita.latizx.usecase.settings

import kotlinx.coroutines.flow.Flow
import uz.gita.latizx.comman.ThemeMode
import uz.gita.latizx.comman.model.map.LocationData

interface SettingsUseCase {
    fun isBalanceDisplayed(): Boolean
    fun changeBalanceDisplayed(): Boolean
    fun getCurrTheme(): String
    fun setCurrTheme(theme: ThemeMode)
    fun getBiometricStatus(): Boolean
    fun changeBiometricStatus()
    fun logOut()
    fun getLocation(): List<LocationData>
}