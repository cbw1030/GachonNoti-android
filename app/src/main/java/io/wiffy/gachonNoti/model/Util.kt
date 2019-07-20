package io.wiffy.gachonNoti.model


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.net.ConnectivityManager
import android.util.Base64
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import io.wiffy.gachonNoti.R
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection


class Util {
    companion object {

        lateinit var sharedPreferences: SharedPreferences

        lateinit var initCount: BooleanArray

        var YEAR = "2019"

        var isLogin = false

        var surfing = false

        var SEMESTER = 1

        var seek = 20

        var firstBoot = true

        var NotificationIndex = 0

        var NewsIndex = 0

        var EventIndex = 0

        var ScholarshipIndex = 0

        var looper = true

        var version = "1.0.0"

        var notificationSet = true

        var state = 0

        var helper = "인터넷 연결을 확인해주세요."

        var theme = "default"

        var made = true

        var novisible = false

        var campus = true

        fun getRandomColorId(): Int = intArrayOf(
            R.color.ran1,
            R.color.ran2,
            R.color.ran3,
            R.color.ran4,
            R.color.ran5,
            R.color.ran6,
            R.color.ran7,
            R.color.ran8
        )[Random().nextInt(8)]

        fun isNetworkConnected(context: Context): Boolean = try {
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null
        } catch (e: Exception) {
            false
        }

        fun matrixToBitmap(matrix: BitMatrix): Bitmap {
            val bmp = Bitmap.createBitmap(matrix.width, matrix.height, Bitmap.Config.RGB_565)
            for (x in 0 until matrix.width)
                for (y in 0 until matrix.height)
                    bmp.setPixel(
                        x, y, if (matrix.get(x, y)) {
                            Color.BLACK
                        } else {
                            Color.WHITE
                        }
                    )
            return bmp
        }

        @SuppressLint("SimpleDateFormat")
        fun classToTime(time: String): LongArray {
            val dt = SimpleDateFormat("HH:mm:ss")
            var start: String
            var end: String
            try {
                when (time) {
                    "A" -> {
                        start = "09:30:00"
                        end = "10:45:00"
                    }
                    "B" -> {
                        start = "11:00:00"
                        end = "12:15:00"
                    }
                    "C" -> {
                        start = "13:00:00"
                        end = "14:15:00"
                    }
                    "D" -> {
                        start = "14:30:00"
                        end = "15:45:00"
                    }
                    "E" -> {
                        start = "16:00:00"
                        end = "17:15:00"
                    }
                    else -> {

                        when (time.toInt()) {
                            in 1..8 -> {
                                start = "${(time.toInt() + 8)}:00:00"
                                end = "${(time.toInt() + 8)}:50:00"
                            }
                            9 -> {
                                start = "17:30:00"
                                end = "18:20:00"
                            }
                            10 -> {
                                start = "18:25:00"
                                end = "19:15:00"
                            }
                            11 -> {
                                start = "19:20:00"
                                end = "20:10:00"
                            }
                            12 -> {
                                start = "20:15:00"
                                end = "21:05:00"
                            }
                            13 -> {
                                start = "21:10:00"
                                end = "22:00:00"
                            }
                            14 -> {
                                start = "22:05:00"
                                end = "22:55:00"
                            }
                            else -> {
                                start = "00:00:00"
                                end = "00:00:00"
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                start = "00:00:00"
                end = "00:00:00"
            }

            return longArrayOf(dt.parse(start).time, dt.parse(end).time)
        }

        const val appConstantPreferences = "GACHONNOTICE"
        const val STATE_NOTIFICATION = 0
        const val STATE_INFORMATION = 1
        const val STATE_SEARCHER = 2
        const val STATE_SETTING = 3
        const val STATE_WEBVIEW = 4
        const val NOT_UPDATED_YET = -99
        const val ACTION_SUCCESS = 0
        const val ACTION_FAILURE = -1

    }
}

class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount!! - 1) {
            outRect.bottom = verticalSpaceHeight
        }
    }
}