package com.chooongg.utils

import android.Manifest.permission.CHANGE_WIFI_STATE
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresPermission
import com.chooongg.ktx.APPLICATION
import com.chooongg.ktx.telephonyManager
import com.chooongg.ktx.wifiManager
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.util.Locale


object DeviceUtils {

    /**
     * 设备是否 Root
     */
    fun isDeviceRooted(): Boolean {
        val su = "su"
        val locations = arrayOf(
            "/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
            "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/",
            "/system/sbin/", "/usr/bin/", "/vendor/bin/"
        )
        for (location in locations) {
            if (File(location + su).exists()) {
                return true
            }
        }
        return false
    }

    /**
     * 是否启用 ADB
     */
    fun isAdbEnabled(): Boolean =
        Settings.Secure.getInt(APPLICATION.contentResolver, Settings.Global.ADB_ENABLED, 0) > 0

    /**
     * 是否启用开发者选项
     */
    fun isDevelopmentSettingsEnabled(): Boolean =
        Settings.Global.getInt(
            APPLICATION.contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
        ) > 0

    /**
     * WIFI 是否启用
     */
    private fun getWifiEnabled(): Boolean = APPLICATION.wifiManager.isWifiEnabled

    /**
     * 设置 WIFI 启用状态
     */
    @RequiresPermission(CHANGE_WIFI_STATE)
    private fun setWifiEnabled(enabled: Boolean) {
        if (enabled == APPLICATION.wifiManager.isWifiEnabled) return
        APPLICATION.wifiManager.setWifiEnabled(enabled)
    }

    /**
     * 是否是平板
     */
    fun isTablet(): Boolean =
        ((Resources.getSystem().configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE)

    /**
     * 是否是模拟器
     */
    fun isEmulator(): Boolean {
        val checkProperty = (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.lowercase(Locale.getDefault()).contains("vbox")
                || Build.FINGERPRINT.lowercase(Locale.getDefault()).contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion") || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith(
            "generic"
        )) || "google_sdk" == Build.PRODUCT
        if (checkProperty) return true
        val operatorName = APPLICATION.telephonyManager.networkOperatorName ?: ""
        val checkOperatorName = operatorName.lowercase(Locale.getDefault()) == "android"
        if (checkOperatorName) return true
        val url = "tel:" + "123456"
        val intent = Intent()
        intent.setData(Uri.parse(url))
        intent.setAction(Intent.ACTION_DIAL)
        val checkDial = intent.resolveActivity(APPLICATION.packageManager) == null
        if (checkDial) return true
        return isEmulatorByCpu()
    }

    private fun isEmulatorByCpu(): Boolean {
        val cpuInfo = readCpuInfo()
        return cpuInfo.contains("intel") || cpuInfo.contains("amd")
    }

    /**
     * 返回 Cpu 信息
     */
    private fun readCpuInfo(): String {
        var result = ""
        try {
            val args = arrayOf("/system/bin/cat", "/proc/cpuinfo")
            val cmd = ProcessBuilder(*args)
            val process = cmd.start()
            val sb = StringBuilder()
            var readLine: String?
            val responseReader = BufferedReader(InputStreamReader(process.inputStream, "utf-8"))
            while (responseReader.readLine().also { readLine = it } != null) {
                sb.append(readLine)
            }
            responseReader.close()
            result = sb.toString().lowercase(Locale.getDefault())
        } catch (ignored: IOException) {
        }
        return result
    }
}