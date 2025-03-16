package uz.gita.latizx.entity.local.pref

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.gita.latizx.comman.SharedPreference
import uz.gita.latizx.comman.ThemeMode
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceHelper @Inject constructor(@ApplicationContext context: Context) : SharedPreference(context) {
    var token: String by strings()

    var refreshToken: String by strings()
    var accessToken: String by strings()
    var pinCode: String by strings()
    var isBalanceDisplayed: Boolean by booleans()
    var theme: String by strings(ThemeMode.SYSTEM.value)
    var statusBiometric: Boolean by booleans()
}