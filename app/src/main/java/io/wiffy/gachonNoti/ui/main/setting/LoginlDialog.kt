package io.wiffy.gachonNoti.ui.main.setting

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import kotlinx.android.synthetic.main.dialog_sign_in.*
import android.app.AlertDialog
import io.wiffy.gachonNoti.model.StudentInformation
import io.wiffy.gachonNoti.ui.main.MainActivity
import kotlinx.android.synthetic.main.dialog_login.view.*


class LoginlDialog(context: Context) : Dialog(context) {
    var tempLogin = false

    @SuppressLint("ApplySharedPref")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_sign_in)

        settingColor(Util.theme)

        logout.setOnClickListener {
            logout()
        }
        isLogin(Util.isLogin)
        gachon.text = Html.fromHtml(
            "<u>가천대학교 홈페이지</u>"
        )
        gachon.setOnClickListener {
            Util.novisible = true
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
            welcome.text = "${Util.sharedPreferences.getString("name", "사용자")}님 안녕하세요!"
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
        } else {
            login.setBackgroundColor(ContextCompat.getColor(context, color))
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
                    Toast.makeText(context, "계정을 확인해주세요.", Toast.LENGTH_SHORT).show()

                } else if (!Util.isNetworkConnected(context)) {
                    Toast.makeText(context, "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
                    dialog.cancel()
                } else {
                    executeLogin(prompt.login_name.text.toString(), prompt.login_password.text.toString())
                }
            }
            prompt.reference.text = Html.fromHtml(
                "<u>참고 코드</u>"
            )
            prompt.reference.setOnClickListener {
                Util.novisible = true
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/wiffy-io/GachonNoti-android")
                    )
                )
            }
            setNegativeButton("취소") { dialog, _ -> dialog.cancel() }

            show()
        }


    }

    private fun executeLogin(id: String, password: String) {
        LoginAsyncTask(id, password, context, this).execute()
    }

    @SuppressLint("ApplySharedPref")
    private fun logout() {
        Util.sharedPreferences.edit().apply {
            remove("id")
            remove("password")
            remove("name")
            remove("number")
            remove("department")
            remove("image")
            putBoolean("login", false)
        }.commit()
        Util.isLogin = false
        isLogin(false)
        (MainActivity.mView).changeTheme()
    }

    @SuppressLint("ApplySharedPref")
    fun saveInformation(information: StudentInformation) {

        with(information) {
            Util.sharedPreferences.edit().apply {
                putString("id", id)
                putString("password", password)
                putString("name", name)
                putString("number", number)
                putString("department", department)
                putString("image", imageURL)
                putBoolean("login", true)
            }.commit()
        }
        Util.isLogin = true
        isLogin(true)
        (MainActivity.mView).changeTheme()
        Toast.makeText(context, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
        dismiss()
    }

    fun loginFailed() {
        Toast.makeText(context, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("ApplySharedPref")
    fun okListen() {

    }
}