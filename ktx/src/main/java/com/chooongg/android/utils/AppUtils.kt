package com.chooongg.android.utils

import android.content.pm.PackageManager
import android.net.Uri
import com.chooongg.android.ktx.APPLICATION
import java.io.File

object AppUtils {

    fun installApp(file: File) {
        APPLICATION.startActivity(IntentUtils.getInstallAppIntent(file))
    }

    fun installApp(uri: Uri) {
        APPLICATION.startActivity(IntentUtils.getInstallAppIntent(uri))
    }

    fun uninstallApp(packageName: String) {
        APPLICATION.startActivity(IntentUtils.getUninstallAppIntent(packageName))
    }

    fun isAppInstalled(packageName: String): Boolean = try {
        APPLICATION.packageManager.getApplicationInfo(packageName, 0).enabled
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }


}