package com.chooongg.android.utils

import android.content.ClipData
import android.content.ClipboardManager.OnPrimaryClipChangedListener
import com.chooongg.android.ktx.APPLICATION
import com.chooongg.android.ktx.clipboardManager


object ClipboardUtils {

    fun copyText(text: CharSequence) {
        APPLICATION.clipboardManager.setPrimaryClip(
            ClipData.newPlainText(APPLICATION.packageName, text)
        )
    }

    fun copyText(label: CharSequence, text: CharSequence) {
        APPLICATION.clipboardManager.setPrimaryClip(
            ClipData.newPlainText(label, text)
        )
    }

    fun clear() {
        APPLICATION.clipboardManager.setPrimaryClip(ClipData.newPlainText(null, ""))
    }

    fun getText(): CharSequence? {
        val clip = APPLICATION.clipboardManager.primaryClip
        return if (clip != null && clip.itemCount > 0) {
            clip.getItemAt(0).coerceToText(APPLICATION)
        } else null
    }

    fun addChangedListener(listener: OnPrimaryClipChangedListener?) {
        APPLICATION.clipboardManager.addPrimaryClipChangedListener(listener)
    }

    fun removeChangedListener(listener: OnPrimaryClipChangedListener?) {
        APPLICATION.clipboardManager.removePrimaryClipChangedListener(listener)
    }
}