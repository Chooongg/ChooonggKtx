package com.chooongg.ktx

import android.widget.Toast
import androidx.annotation.GravityInt
import androidx.annotation.StringRes

/**
 * Toast 唯一实例
 */
private var toast: Toast? = null

/**
 * 展示 Toast
 */
fun showToast(text: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    cancelToast()
    toast = Toast.makeText(APPLICATION, text, duration).apply {
        show()
    }
}

/**
 * 展示 Toast
 */
fun showToast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    cancelToast()
    toast = Toast.makeText(APPLICATION, resId, duration).apply {
        show()
    }
}

/**
 * 展示 Toast
 */
fun showToast(
    text: CharSequence?,
    @GravityInt gravity: Int,
    xOffset: Int = 0,
    yOffset: Int = 0,
    duration: Int = Toast.LENGTH_SHORT
) {
    cancelToast()
    toast = Toast.makeText(APPLICATION, text, duration).apply {
        setGravity(gravity, xOffset, yOffset)
        show()
    }
}

/**
 * 展示 Toast
 */
fun showToast(
    @StringRes resId: Int,
    @GravityInt gravity: Int,
    xOffset: Int = 0,
    yOffset: Int = 0,
    duration: Int = Toast.LENGTH_SHORT
) {
    cancelToast()
    toast = Toast.makeText(APPLICATION, resId, duration).apply {
        setGravity(gravity, xOffset, yOffset)
        show()
    }
}


/**
 * 取消Toast
 */
fun cancelToast() {
    toast?.cancel()
    toast = null
}