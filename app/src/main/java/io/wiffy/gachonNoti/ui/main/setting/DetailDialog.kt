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
import de.hdodenhof.circleimageview.CircleImageView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import kotlinx.android.synthetic.main.dialog_detailsetting.*
import android.app.AlertDialog
import io.wiffy.gachonNoti.model.StudentInformation
import kotlinx.android.synthetic.main.dialog_login.view.*


class DetailDialog(context: Context) : Dialog(context) {
    lateinit var list: ArrayList<CircleImageView>
    var index = -1
    var preIndex = -1
    var setNum = 0
    var tempLogin = false

    @SuppressLint("ApplySharedPref")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_detailsetting)

        setNum = Util.seek
        setText.text = setNum.toString()

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
        toLeft.setOnClickListener {
            if (setNum > 10) {
                setNum -= 5
                setText.text = setNum.toString()
            }
        }

        toRight.setOnClickListener {
            if (setNum < 100) {
                setNum += 5
                setText.text = setNum.toString()
            }
        }


        index = when (Util.theme) {
            "red" -> 1
            "green" -> 2
            else -> 0
        }
        preIndex = index

        list = ArrayList()
        list.add(defaultColor)
        list.add(redColor)
        list.add(greenColor)
        list[index].borderWidth = 4
        for (x in 0 until list.size) {
            list[x].setOnClickListener {
                for (v in 0 until list.size) {
                    if (x == v)
                        list[v].borderWidth = 4
                    else
                        list[v].borderWidth = 0
                }
                index = x
                settingColor(x)
            }
        }

    }

    fun setListener(positiveListener: View.OnClickListener, negativeListener: View.OnClickListener) {
        OK.setOnClickListener(positiveListener)
        Cancel.setOnClickListener(negativeListener)
    }

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

    private fun settingColor(int: Int) {
        val color = when (int) {
            2 -> R.color.green
            1 -> R.color.red
            else -> R.color.main2Blue
        }
        val style = when (color) {
            R.color.green -> R.drawable.dialog_button_green
            R.color.red -> R.drawable.dialog_button_red
            else -> R.drawable.dialog_button_default
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            OK.setBackgroundResource(style)
            Cancel.setBackgroundResource(style)
            login.setBackgroundResource(style)
        } else {
            OK.setBackgroundColor(ContextCompat.getColor(context, color))
            Cancel.setBackgroundColor(ContextCompat.getColor(context, color))
            login.setBackgroundColor(ContextCompat.getColor(context, color))
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
            OK.setBackgroundResource(style)
            Cancel.setBackgroundResource(style)
            login.setBackgroundResource(style)
        } else {
            OK.setBackgroundColor(ContextCompat.getColor(context, color))
            Cancel.setBackgroundColor(ContextCompat.getColor(context, color))
            login.setBackgroundColor(ContextCompat.getColor(context, color))
        }
    }

    @SuppressLint("InflateParams")
    private fun login() {
        val li = LayoutInflater.from(context)
        val prompt = li.inflate(R.layout.dialog_login, null)
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setView(prompt)

        //user.setText(Login_USER); //login_USER and PASS are loaded from previous session (optional)
        //pass.setText(Login_PASS);
        alertDialogBuilder.setTitle("계정 정보")
        alertDialogBuilder.setCancelable(false)
            .setPositiveButton("로그인") { dialog, _ ->
                if (Util.isNetworkConnected(context)) {
                    executeLogin(prompt.login_name.text.toString(), prompt.login_password.text.toString())
                } else {
                    Toast.makeText(context, "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
                    dialog.cancel()
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
        alertDialogBuilder.setNegativeButton("취소") { dialog, _ -> dialog.cancel() }

        alertDialogBuilder.show()

    }

    private fun executeLogin(id: String, password: String) {
        LoginAsyncTask(id, password, context, this).execute()
        TODO()
    }

    @SuppressLint("ApplySharedPref")
    private fun logout() {
        Util.sharedPreferences.edit().remove("id").commit()
        Util.sharedPreferences.edit().remove("password").commit()
        Util.sharedPreferences.edit().remove("name").commit()
        Util.sharedPreferences.edit().remove("number").commit()
        Util.sharedPreferences.edit().putBoolean("login", false).commit()
        Util.isLogin = false
        isLogin(false)
    }

    fun saveInformation(information: StudentInformation)
    {
        isLogin(true)
        Toast.makeText(context,"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show()
    }
    fun loginFailed()
    {
        Toast.makeText(context,"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("ApplySharedPref")
    fun okListen() {
        Util.seek = setNum
        Util.theme = when (index) {
            1 -> "red"
            2 -> "green"
            else -> "default"
        }
        Util.sharedPreferences.edit().putInt("seek", Util.seek).commit()
        Util.sharedPreferences.edit().putString("theme", Util.theme).commit()
    }
}