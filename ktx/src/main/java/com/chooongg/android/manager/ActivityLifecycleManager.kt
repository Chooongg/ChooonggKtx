package com.chooongg.android.manager

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.chooongg.android.AppStatusChangedCallback
import java.util.LinkedList

internal object ActivityLifecycleManager : Application.ActivityLifecycleCallbacks {

    private val statusCallbacks = ArrayList<AppStatusChangedCallback>()

    internal val activityTask = LinkedList<Activity>()

    fun addOnAppStatusChangedCallback(callback: AppStatusChangedCallback) {
        statusCallbacks.add(callback)
    }

    fun removeOnAppStatusChangedCallback(callback: AppStatusChangedCallback) {
        statusCallbacks.remove(callback)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activityTask.isEmpty()) postStatus(activity, true)
        activityTask.add(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {
        activityTask.remove(activity)
    }

    override fun onActivityStarted(activity: Activity) = Unit
    override fun onActivityResumed(activity: Activity) = Unit
    override fun onActivityPaused(activity: Activity) = Unit
    override fun onActivityStopped(activity: Activity) = Unit
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

    private fun postStatus(activity: Activity, isForeground: Boolean) {
        statusCallbacks.forEach { it.invoke(activity, isForeground) }
    }
}