package com.chooongg.android

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.chooongg.android.manager.ApplicationManager
import com.chooongg.android.manager.NightModeManager

@Suppress("unused")
class ChooonggUtilsInitializer : Initializer<String> {
    override fun create(context: Context): String {
        ApplicationManager.initialize(context as Application)
        NightModeManager.initialize(context)
        return "ChooonggUtils is initialized"
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}