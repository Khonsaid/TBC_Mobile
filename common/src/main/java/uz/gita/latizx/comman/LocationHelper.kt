package uz.gita.latizx.comman

import android.content.Context
import android.content.SharedPreferences
import java.util.Locale

object LocationHelper {
    private lateinit var localPref: SharedPreferences

    fun attach(context: Context): Context {
        localPref = context.getSharedPreferences("locale_pref", Context.MODE_PRIVATE)

        val savedLanguage = getLang()
        return setLocation(context, savedLanguage)
    }

    fun getLang(): String = localPref.getString("LANGUAGE", "uz") ?: "uz"

    fun setLocation(context: Context, lang: String): Context {
        localPref.edit().putString("LANGUAGE", lang).apply()
        updateResource(context, lang)
        return context
    }

    private fun updateResource(context: Context, lang: String) {
        val locale = Locale(lang)
        val resource = context.resources

        val configuration = resource.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        context.createConfigurationContext(configuration)
        resource.updateConfiguration(configuration, resource.displayMetrics)
    }
}