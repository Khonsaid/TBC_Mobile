package uz.gita.latizx.usecase.settings.impl

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import uz.gita.latizx.comman.ThemeMode
import uz.gita.latizx.comman.model.map.LocationData
import uz.gita.latizx.entity.local.pref.PreferenceHelper
import uz.gita.latizx.usecase.settings.SettingsUseCase
import javax.inject.Inject

class SettingsUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
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

    override fun getLocation(): List<LocationData> {
        return try {
            val jsonString = context.assets.open("tbc_map.json").bufferedReader().use { it.readText() }
            Json { ignoreUnknownKeys = true }.decodeFromString(jsonString)
        } catch (e: Exception) {
            emptyList()
        }
    }
}