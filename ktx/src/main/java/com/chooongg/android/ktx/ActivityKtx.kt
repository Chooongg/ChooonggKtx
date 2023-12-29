package com.chooongg.android.ktx

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build

/**
 * 获取Activity信息
 */
fun Activity.loadActivityInfo(): ActivityInfo {
    val activityInfo = ComponentName(this, javaClass).let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getActivityInfo(it, PackageManager.ComponentInfoFlags.of(0))
        } else packageManager.getActivityInfo(it, 0)
    }
    return activityInfo
}

/**
 * 加载Activity标签
 */
fun Activity.loadActivityLabel(): CharSequence {
    val activityInfo = ComponentName(this, javaClass).let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getActivityInfo(it, PackageManager.ComponentInfoFlags.of(0))
        } else packageManager.getActivityInfo(it, 0)
    }
    return activityInfo.loadLabel(packageManager)
}

/**
 * 获取Activity
 */
fun Context.getActivity(): Activity? {
    if (this is Activity) return this
    var context: Context? = this
    while (context != null) {
        if (context is Activity) return context
        context = (context as? ContextWrapper)?.baseContext
    }
    return null
}

