package com.chooongg.ktx

import android.os.Build
import androidx.core.net.toUri
import java.io.File
import java.io.FileNotFoundException

fun File.existsCompat(): Boolean {
    if (exists()) return true
    return existsApi29()
}

private fun File.existsApi29(): Boolean {
    if (Build.VERSION.SDK_INT >= 29) {
        try {
            APPLICATION.contentResolver.openAssetFileDescriptor(toUri(), "r")?.use {
                return true
            } ?: return false
        } catch (e: FileNotFoundException) {
            return false
        }
    }
    return false
}