package com.chooongg.ktx

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Build
import android.view.Window
import android.widget.FrameLayout
import androidx.appcompat.widget.ContentFrameLayout

inline val Activity.decorView: FrameLayout get() = window.decorView as FrameLayout
inline val Activity.contentView: ContentFrameLayout get() = findViewById(Window.ID_ANDROID_CONTENT)

/**
 * Activity是否活动
 */
fun Activity?.isLive() = this != null && !isFinishing && !isDestroyed

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

/**
 * 软键盘显示监听
 */
fun Activity.onKeyboardShowListener(listener: (isShow: Boolean) -> Unit) {
    contentView.viewTreeObserver.addOnGlobalLayoutListener {
        val rect = Rect()
        window.decorView.getWindowVisibleDisplayFrame(rect)
        val screenHeight = window.decorView.rootView.height
        val heightDifference = screenHeight - rect.bottom
        if (heightDifference > screenHeight / 3) {
            listener.invoke(true)
        } else {
            listener.invoke(false)
        }
    }
}