package com.chooongg.android.utils

import android.media.AudioManager
import android.os.Build
import com.chooongg.android.ktx.APPLICATION
import com.chooongg.android.ktx.audioManager

object VolumeUtils {

    /**
     * 获取音量
     * @see AudioManager.STREAM_VOICE_CALL
     * @see AudioManager.STREAM_SYSTEM
     * @see AudioManager.STREAM_RING
     * @see AudioManager.STREAM_MUSIC
     * @see AudioManager.STREAM_ALARM
     * @see AudioManager.STREAM_NOTIFICATION
     * @see AudioManager.STREAM_DTMF
     * @see AudioManager.STREAM_ACCESSIBILITY
     */
    fun getVolume(streamType: Int): Int = APPLICATION.audioManager.getStreamVolume(streamType)

    /**
     * 设置音量
     * @see AudioManager.STREAM_VOICE_CALL
     * @see AudioManager.STREAM_SYSTEM
     * @see AudioManager.STREAM_RING
     * @see AudioManager.STREAM_MUSIC
     * @see AudioManager.STREAM_ALARM
     * @see AudioManager.STREAM_NOTIFICATION
     * @see AudioManager.STREAM_DTMF
     * @see AudioManager.STREAM_ACCESSIBILITY
     * @see AudioManager.FLAG_SHOW_UI
     * @see AudioManager.FLAG_ALLOW_RINGER_MODES
     * @see AudioManager.FLAG_PLAY_SOUND
     * @see AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE
     * @see AudioManager.FLAG_VIBRATE
     */
    @Throws(SecurityException::class)
    fun setVolume(streamType: Int, volume: Int, flags: Int) {
        APPLICATION.audioManager.setStreamVolume(streamType, volume, flags)
    }

    /**
     * 获取最大音量
     * @see AudioManager.STREAM_VOICE_CALL
     * @see AudioManager.STREAM_SYSTEM
     * @see AudioManager.STREAM_RING
     * @see AudioManager.STREAM_MUSIC
     * @see AudioManager.STREAM_ALARM
     * @see AudioManager.STREAM_NOTIFICATION
     * @see AudioManager.STREAM_DTMF
     * @see AudioManager.STREAM_ACCESSIBILITY
     */
    fun getMaxVolume(streamType: Int): Int = APPLICATION.audioManager.getStreamMaxVolume(streamType)

    /**
     * 获取最小音量
     * @see AudioManager.STREAM_VOICE_CALL
     * @see AudioManager.STREAM_SYSTEM
     * @see AudioManager.STREAM_RING
     * @see AudioManager.STREAM_MUSIC
     * @see AudioManager.STREAM_ALARM
     * @see AudioManager.STREAM_NOTIFICATION
     * @see AudioManager.STREAM_DTMF
     * @see AudioManager.STREAM_ACCESSIBILITY
     */
    fun getMinVolume(streamType: Int): Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        APPLICATION.audioManager.getStreamMinVolume(streamType)
    } else 0
}