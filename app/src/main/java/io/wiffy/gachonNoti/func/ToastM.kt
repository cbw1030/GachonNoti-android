package io.wiffy.gachonNoti.func

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

fun makeToast(context: Context, str: String) = Handler(Looper.getMainLooper()).post {
    Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
}