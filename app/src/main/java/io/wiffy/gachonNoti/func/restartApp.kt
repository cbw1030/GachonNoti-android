package io.wiffy.gachonNoti.func

import android.app.Activity
import android.app.AlertDialog
import android.content.Context

fun doneLogin(act:Activity,cnt:Context){
    AlertDialog.Builder(act).apply {
        setTitle("로그인 세션이 만료되었습니다.")
        setMessage("앱을 재실행 합니다.")
        setPositiveButton(
            "OK"
        ) { _, _ -> restartApp(cnt)}
    }.show()
}
