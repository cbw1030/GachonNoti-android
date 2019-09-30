package io.wiffy.gachonNoti.function

import com.palecosmos.escapableforeach.escapableForEach
import java.lang.Exception
import java.net.NetworkInterface
import java.util.*

fun getMACAddress(): String {
    try {
        for (it in Collections.list(NetworkInterface.getNetworkInterfaces())) {
            if (it != null) {
                if (!it.name.equals("wlan0", true)) continue
            }
            val mac = it.hardwareAddress ?: return ""
            val buf = StringBuilder()
            mac.escapableForEach { _, element ->
                buf.append(String.format("%02X:", element))
                true
            }
            if (buf.isNotEmpty()) buf.deleteCharAt(buf.length - 1)
            return buf.toString()
        }
    } catch (e: Exception) {
    }
    return "park"
}

