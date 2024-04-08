package com.chooongg.manager

import android.app.Application

internal object ApplicationManager {
    internal var application: Application? = null
        private set

    internal fun initialize(application: Application) {
        ApplicationManager.application = application.apply {
            registerActivityLifecycleCallbacks(ActivityLifecycleManager)
        }
    }
}