package com.chooongg.android.utils

import android.content.pm.PackageManager
import android.net.Uri
import android.text.TextUtils
import android.view.View
import com.chooongg.android.ktx.APPLICATION
import java.io.File

object AppUtils {

    /**
     * 安装应用
     */
    fun installApp(file: File) {
        APPLICATION.startActivity(IntentUtils.getInstallAppIntent(file))
    }

    /**
     * 安装应用
     */
    fun installApp(uri: Uri) {
        APPLICATION.startActivity(IntentUtils.getInstallAppIntent(uri))
    }

    /**
     * 卸载应用
     */
    fun uninstallApp(packageName: String) {
        APPLICATION.startActivity(IntentUtils.getUninstallAppIntent(packageName))
    }

    /**
     * App是否安装
     */
    fun isAppInstalled(packageName: String): Boolean = try {
        APPLICATION.packageManager.getApplicationInfo(packageName, 0).enabled
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }

    /**
     * App布局是否是右到左
     */
    fun isAppLayoutRtl(): Boolean =
        TextUtils.getLayoutDirectionFromLocale(APPLICATION.resources.configuration.locales[0]) == View.LAYOUT_DIRECTION_RTL
}