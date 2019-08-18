package io.wiffy.gachonNoti.func

import java.lang.Exception
import java.net.NetworkInterface
import java.util.*

fun getMACAddress(): String {
    try {
        val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
        for (`interface` in interfaces) {
            if (`interface` != null) {
                if (!`interface`.name.equals("wlan0", true)) continue
            }
            val mac = `interface`.hardwareAddress ?: return ""
            val buf = StringBuilder()
            for (idx in 0 until mac.size) buf.append(String.format("%02X:", mac[idx]))
            if (buf.isNotEmpty()) buf.deleteCharAt(buf.length - 1)
            return buf.toString()
        }
    } catch (e: Exception) {
    }
    return "park"
}