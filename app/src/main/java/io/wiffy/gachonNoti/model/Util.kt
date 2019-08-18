package io.wiffy.gachonNoti.model


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.net.ConnectivityManager
import android.util.Base64
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.common.BitMatrix
import io.wiffy.gachonNoti.R
import java.lang.Exception
import java.net.NetworkInterface
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.HashSet


@Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
class Util {
    companion object {

        var YEAR = "2019"

        var isLogin = false

        var surfing = false

        var SEMESTER = 1

        var firstBoot = true

        var looper = true

        var version = "1.0.0"

        var notificationSet = true

        var state = 0

        var helper = "인터넷 연결을 확인해주세요."

        var theme = "default"

        var novisible = false

        var campus = true

    }
}
