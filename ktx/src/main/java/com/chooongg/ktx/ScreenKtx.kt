package com.chooongg.ktx

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.RequiresPermission
import kotlin.math.abs

fun Context.getScreenWidth() = resources.displayMetrics.widthPixels

fun Context.getScreenHeight() = resources.displayMetrics.heightPixels

fun getScreenDisplayMetrics(): DisplayMetrics = Resources.getSystem().displayMetrics

fun getScreenDensity() = Resources.getSystem().displayMetrics.density

fun getScreenDensityDpi() = Resources.getSystem().displayMetrics.densityDpi

fun getScreenXDpi() = Resources.getSystem().displayMetrics.xdpi

fun getScreenYDpi() = Resources.getSystem().displayMetrics.ydpi

@Suppress("DEPRECATION")
fun getScreenScaledDensity() = Resources.getSystem().displayMetrics.scaledDensity

fun Context.isLandscape() =
    resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

fun Context.isPortrait() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

/**
 * 获取状态栏高度
 */
@SuppressLint("DiscouragedApi", "InternalInsetResource")
fun Context.getStatusBarHeight(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val manager = getSystemService(WindowManager::class.java)
        val metrics = manager.currentWindowMetrics
        val windowInsets = metrics.windowInsets
        val insets = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.statusBars())
        abs(insets.bottom - insets.top)
    } else {
        val resources = Resources.getSystem()
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        resources.getDimensionPixelSize(resourceId)
    }
}

/**
 * 屏幕是否亮屏
 */
fun isScreenBrightOn() = APPLICATION.powerManager.isInteractive

/**
 * 屏幕是否熄灭
 */
fun isScreenBrightOff() = !isScreenBrightOn()

/**
 * 屏幕是否锁屏
 */
fun isScreenLocked() = APPLICATION.keyguardManager.isKeyguardLocked

/**
 * 屏幕是否解锁
 */
fun isScreenUnlocked() = !isScreenLocked()

/**
 * 是否保持屏幕常亮，只作用于当前窗口
 */
var Activity.isKeepScreenOn: Boolean
    get() {
        val flag = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        return (window.attributes.flags and flag) == flag
    }
    set(value) {
        when (value) {
            true -> window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            false -> window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

/**
 * 获取自动锁屏时间
 * @throws Settings.SettingNotFoundException
 */
fun getScreenAutoLockTime() = try {
    Settings.System.getInt(
        APPLICATION.contentResolver, Settings.System.SCREEN_OFF_TIMEOUT
    )
} catch (e: Settings.SettingNotFoundException) {
    e.printStackTrace()
    -1
}

/**
 * 设置自动锁屏时间
 * @return 设置成功返回true
 */
@RequiresPermission(android.Manifest.permission.WRITE_SETTINGS)
fun setScreenAutoLockTime(time: Int): Boolean = Settings.System.putInt(
    APPLICATION.contentResolver, Settings.System.SCREEN_OFF_TIMEOUT, time
)

/**
 * 设置永不自动锁屏，即自动锁屏时间为Int.MAX_VALUE
 * @return 设置成功返回true
 */
@RequiresPermission(android.Manifest.permission.WRITE_SETTINGS)
fun setScreenAutoLockNever(): Boolean = setScreenAutoLockTime(Int.MAX_VALUE)
