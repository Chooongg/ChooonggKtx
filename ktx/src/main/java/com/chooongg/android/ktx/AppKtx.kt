package com.chooongg.android.ktx

import android.app.Activity
import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import com.chooongg.android.manager.ActivityLifecycleManager
import com.chooongg.android.manager.ApplicationManager
import java.io.File

val APPLICATION get() = ApplicationManager.application!!

/**
 * 获取Activity栈
 */
val ACTIVITY_STACK: List<Activity> get() = ActivityLifecycleManager.activityTask

/**
 * 获取Activity栈中的栈顶
 */
val ACTIVITY_STACK_TOP
    get() = if (ActivityLifecycleManager.activityTask.isEmpty()) null
    else ActivityLifecycleManager.activityTask.last

/**
 * 判断是否在主进程
 */
val Application.isMainProcess: Boolean
    get() {
        val processInfo = activityManager.runningAppProcesses
        val mainProcessName = packageName
        val pid = android.os.Process.myPid()
        processInfo.forEach { if (it.pid == pid && mainProcessName == it.processName) return true }
        return false
    }

/**
 * 判断 Service 是否已经运行
 */
@Suppress("DEPRECATION")
fun Application.isServiceExisted(className: String): Boolean {
    val serviceList = activityManager.getRunningServices(Int.MAX_VALUE)
    val uid = android.os.Process.myUid()
    serviceList.forEach { if (it.uid == uid && it.service.className == className) return true }
    return false
}

/**
 * 判断是否是Debug版本
 *
 * @param packageName 包名
 */
fun isAppDebug(packageName: String = APPLICATION.packageName): Boolean {
    if (packageName.isEmpty()) return false
    return try {
        val ai = APPLICATION.packageManager.getApplicationInfo(packageName, 0)
        ai.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        false
    }
}

/**
 * Debug代码块
 */
fun debug(init: () -> Unit) = if (isAppDebug()) init() else Unit

/**
 * Release代码块
 */
fun release(init: () -> Unit) = if (!isAppDebug()) init() else Unit

