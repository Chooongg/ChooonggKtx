package com.chooongg.android.manager

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.LinkedList

internal object ActivityLifecycleManager : Application.ActivityLifecycleCallbacks {

    internal val activityTask = LinkedList<Activity>()

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
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
}