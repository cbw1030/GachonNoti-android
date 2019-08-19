package io.wiffy.gachonNoti.ui.main.setting.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Component
import kotlinx.android.synthetic.main.dialog_sign_in.*
import android.app.AlertDialog
import io.wiffy.gachonNoti.func.encrypt
import io.wiffy.gachonNoti.func.getMACAddress
import io.wiffy.gachonNoti.func.isNetworkConnected
import io.wiffy.gachonNoti.func.sharedPreferences
import io.wiffy.gachonNoti.model.StudentInformation
import io.wiffy.gachonNoti.model.SuperContract
import io.wiffy.gachonNoti.ui.main.MainActivity
import kotlinx.android.synthetic.main.dialog_login.view.*


class LoginDialog(context: Context) : SuperContract.SuperDialog(context) {
    var tempLogin = false

    @SuppressLint("ApplySharedPref", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_sign_in)

        settingColor(Component.theme)

        logout.setOnClickListener {
            logout()
        }
        isLogin(Component.isLogin)
        gachon.text = Html.fromHtml(
            "<u>가천대학교 홈페이지</u>"
        )
        gachonname.text = "${sharedPreferences.getString("name", "") ?: ""}님 안녕하세요!"
        gachon.setOnClickListener {
            Component.noneVisible = true
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://gachon.ac.kr/")))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun isLogin(bool: Boolean) {
        login.setOnClickListener {
            login()
        }
        tempLogin = bool
        if (bool) {
            frame_logined.visibility = View.VISIBLE
            frame_none_logined.visibility = View.GONE
        } else {
            frame_logined.visibility = View.GONE
            frame_none_logined.visibility = View.VISIBLE
        }
    }


    private fun settingColor(str: String) {
        val color = when (str) {
            "green" -> R.color.green
            "red" -> R.color.red
            else -> R.color.main2Blue
        }
        val style = when (color) {
            R.color.green -> R.drawable.dialog_button_green
            R.color.red -> R.drawable.dialog_button_red
            else -> R.drawable.dialog_button_default
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            login.setBackgroundResource(style)
            logout.setBackgroundResource(style)
        } else {
            login.setBackgroundColor(ContextCompat.getColor(context, color))
            logout.setBackgroundColor(ContextCompat.getColor(context, color))
        }
    }

    @SuppressLint("InflateParams")
    private fun login() {

        val li = LayoutInflater.from(context)
        val prompt = li.inflate(R.layout.dialog_login, null)
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.run {
            setView(prompt)

            setTitle("계정 정보")
            setCancelable(false)
            setPositiveButton("로그인") { dialog, _ ->
                if (prompt.login_name.text.toString().isBlank() || prompt.login_password.text.toString().isBlank()) {
                    toast("계정을 확인해주세요.")
                } else if (!isNetworkConnected(context)) {
                    toast("인터넷 연결을 확인해주세요.")
                    dialog.cancel()
                } else {
                    executeLogin(prompt.login_name.text.toString(), prompt.login_password.text.toString())
                }
            }
            setNegativeButton("취소") { dialog, _ -> dialog.cancel() }

            show()
        }

    }

    private fun executeLogin(id: String, password: String) {
        LoginAsyncTask(id, password, this).execute()
    }

    fun sendContext(): Context = context

    @SuppressLint("ApplySharedPref")
    private fun logout() {
        sharedPreferences.edit().apply {
            remove("id")
            remove("password")
            remove("name")
            remove("number")
            remove("department")
            remove("image")
            putBoolean("login", false)
        }.commit()
        Component.isLogin = false
        isLogin(false)
        (MainActivity.mView).allThemeChange()
        toast("로그아웃 되었습니다.")
        dismiss()
    }

    @SuppressLint("ApplySharedPref", "SetTextI18n")
    fun saveInformation(information: StudentInformation) {

        with(information) {
            sharedPreferences.edit().apply {
                putString("id", id)
                putString("password", encrypt(password!!, getMACAddress()))
                putString("name", name)
                putString("number", number)
                putString("department", department)
                putString("image", imageURL)
                putBoolean("login", true)
            }.commit()
        }
        gachonname.text = "${sharedPreferences.getString("name", "") ?: ""}님 안녕하세요!"
        Component.isLogin = true
        isLogin(true)
        (MainActivity.mView).allThemeChange()
        toast("로그인에 성공하였습니다.")
        dismiss()
    }

    fun loginFailed() = toast("로그인에 실패하였습니다.")


}