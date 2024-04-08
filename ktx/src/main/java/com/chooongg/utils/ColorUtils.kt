package com.chooongg.utils

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import com.google.android.material.color.MaterialColors


object ColorUtils {

    /**
     * 设置透明度
     */
    fun setAlphaComponent(
        @ColorInt color: Int,
        @androidx.annotation.IntRange(from = 0x0, to = 0xFF) alpha: Int
    ) = color and 0x00ffffff or (alpha shl 24)

    /**
     * 设置透明度
     */
    fun setAlphaComponent(
        @ColorInt color: Int,
        @FloatRange(from = 0.0, to = 1.0) alpha: Float
    ) = color and 0x00ffffff or ((alpha * 255.0f + 0.5f).toInt() shl 24)

    /**
     * RGB颜色转换为字符串
     */
    fun int2RgbString(@ColorInt color: Int): String {
        var colorInt = color
        colorInt = colorInt and 0x00ffffff
        var colorStr = Integer.toHexString(colorInt)
        while (colorStr.length < 6) {
            colorStr = "0$colorStr"
        }
        return "#$colorStr"
    }

    /**
     * ARGB颜色转换为字符串
     */
    fun int2ArgbString(@ColorInt colorInt: Int): String {
        var color = Integer.toHexString(colorInt)
        while (color.length < 6) {
            color = "0$color"
        }
        while (color.length < 8) {
            color = "f$color"
        }
        return "#$color"
    }

    /**
     * 获取随机颜色
     */
    fun getRandomColor(supportAlpha: Boolean = true): Int {
        val high = if (supportAlpha) (Math.random() * 0x100).toInt() shl 24 else -0x1000000
        return high or (Math.random() * 0x1000000).toInt()
    }
}