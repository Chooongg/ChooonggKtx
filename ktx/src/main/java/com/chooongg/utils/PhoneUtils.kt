package com.chooongg.utils

import android.telephony.TelephonyManager
import com.chooongg.ktx.APPLICATION
import com.chooongg.ktx.telephonyManager

object PhoneUtils {

    /**
     * 判断 sim 卡是否准备好
     */
    fun isSimCardReady(): Boolean =
        APPLICATION.telephonyManager.simState == TelephonyManager.SIM_STATE_READY
}