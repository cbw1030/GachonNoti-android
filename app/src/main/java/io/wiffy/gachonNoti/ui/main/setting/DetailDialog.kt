package io.wiffy.gachonNoti.ui.main.setting

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import de.hdodenhof.circleimageview.CircleImageView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import kotlinx.android.synthetic.main.dialog_detailsetting.*


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
            Toast.makeText(context, "로그인하자", Toast.LENGTH_SHORT).show()
        }
        tempLogin = bool
        if (bool) {
            frame_logined.visibility = View.VISIBLE
            frame_none_logined.visibility = View.GONE
            login()
        } else {
            frame_logined.visibility = View.GONE
            frame_none_logined.visibility = View.VISIBLE
            nonLogin()
        }
    }

    private fun settingColor(int:Int)
    {
        val color = when (int) {
            2 -> R.color.green
            1 -> R.color.red
            else -> R.color.main2Blue
        }
        OK.setBackgroundColor(ContextCompat.getColor(context, color))
        Cancel.setBackgroundColor(ContextCompat.getColor(context, color))
        login.setBackgroundColor(ContextCompat.getColor(context, color))
    }

    private fun settingColor(str: String) {
        val color = when (str) {
            "green" -> R.color.green
            "red" -> R.color.red
            else -> R.color.main2Blue
        }

        OK.setBackgroundColor(ContextCompat.getColor(context, color))
        Cancel.setBackgroundColor(ContextCompat.getColor(context, color))
        login.setBackgroundColor(ContextCompat.getColor(context, color))
    }

    private fun login() {

    }

    private fun nonLogin() {

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