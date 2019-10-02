package io.wiffy.gachonNoti.utils

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.common.BitMatrix

fun BitMatrix.toBitmap(): Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565).also {
    for (x in 0 until width) {
        for (y in 0 until height) {
            it.setPixel(
                x, y, if (get(x, y)) {
                    Color.BLACK
                } else {
                    Color.WHITE
                }
            )
        }
    }
}