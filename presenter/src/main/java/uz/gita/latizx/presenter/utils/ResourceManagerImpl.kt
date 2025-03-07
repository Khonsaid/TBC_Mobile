package uz.gita.latizx.presenter.utils

import android.content.Context

class ResourceManagerImpl(private val context: Context) : ResourceManager {
    override fun getString(resId: Int): String {
        return context.getString(resId)
    }
}