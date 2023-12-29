package com.chooongg.android.utils

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.annotation.RequiresPermission
import androidx.core.content.FileProvider
import com.chooongg.android.ktx.APPLICATION
import com.chooongg.android.ktx.existsCompat
import java.io.File

object IntentUtils {

    /**
     * 获取安装应用意图
     */
    fun getInstallAppIntent(file: File): Intent? {
        if (file.existsCompat()) return null
        val uri = FileProvider.getUriForFile(
            APPLICATION, "${APPLICATION.packageName}.chooongg.fileProvider", file
        )
        return getInstallAppIntent(uri)
    }

    /**
     * 获取安装应用意图
     */
    fun getInstallAppIntent(uri: Uri?) = if (uri == null) null else Intent(Intent.ACTION_VIEW)
        .setDataAndType(uri, "application/vnd.android.package-archive")
        .setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    /**
     * 获取卸载应用意图
     */
    fun getUninstallAppIntent(packageName: String) = Intent(Intent.ACTION_DELETE)
        .setData(Uri.parse("package:${packageName}"))
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    /**
     * 获取应用具体设置意图
     */
    fun getLaunchAppDetailsSettingsIntent(packageName: String, isNewTask: Boolean = false) =
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .setData(Uri.parse("package:${packageName}")).apply {
                if (isNewTask) addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

    /**
     * 获取分享文本意图
     */
    fun getShareTextIntent(content: String) = Intent(Intent.ACTION_SEND)
        .setType("text/plain")
        .putExtra(Intent.EXTRA_TEXT, content)
        .let { Intent.createChooser(it, "") }
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    /**
     * 获取分享图片意图
     */
    fun getShareImageIntent(file: File) = getShareTextImageIntent("", file)

    /**
     * 获取分享图片意图
     */
    fun getShareImageIntent(uri: Uri) = getShareTextImageIntent("", uri)

    /**
     * 获取分享文本图片意图
     */
    fun getShareTextImageIntent(content: String, file: File) {
        getShareTextImageIntent(
            content,
            FileProvider.getUriForFile(
                APPLICATION, "${APPLICATION.packageName}.chooongg.fileProvider", file
            )
        )
    }

    /**
     * 获取分享文本图片意图
     */
    fun getShareTextImageIntent(content: String, uri: Uri) = Intent(Intent.ACTION_SEND)
        .putExtra(Intent.EXTRA_TEXT, content)
        .putExtra(Intent.EXTRA_STREAM, uri)
        .setType("image/*")
        .let { Intent.createChooser(it, "") }
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    /**
     * 获取拨号盘意图
     */
    fun getDialIntent(phoneNumber: String) = Intent(Intent.ACTION_DIAL)
        .setData(Uri.parse("tel:${Uri.encode(phoneNumber)}"))
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    /**
     * 获取打电话意图
     */
    @RequiresPermission(Manifest.permission.CALL_PHONE)
    fun getCallIntent(phoneNumber: String) = Intent(Intent.ACTION_CALL)
        .setData(Uri.parse("tel:${Uri.encode(phoneNumber)}"))
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    /**
     * 获取发短信意图
     */
    fun getSendSmsIntent(phoneNumber: String, content: String) = Intent(Intent.ACTION_SENDTO)
        .setData(Uri.parse("smsto:${Uri.encode(phoneNumber)}"))
        .putExtra("sms_body", content)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}