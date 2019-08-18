package io.wiffy.gachonNoti.func

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.common.BitMatrix

fun matrixToBitmap(matrix: BitMatrix): Bitmap =
    Bitmap.createBitmap(matrix.width, matrix.height, Bitmap.Config.RGB_565).apply {
        for (x in 0 until matrix.width)
            for (y in 0 until matrix.height)
                setPixel(
                    x, y, if (matrix.get(x, y)) {
                        Color.BLACK
                    } else {
                        Color.WHITE
                    }
                )
    }