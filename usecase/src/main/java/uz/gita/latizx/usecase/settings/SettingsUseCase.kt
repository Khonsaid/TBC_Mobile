package uz.gita.latizx.usecase.settings

interface SettingsUseCase {
    fun isBalanceDisplayed(): Boolean
    fun changeBalanceDisplayed(): Boolean
}