package io.wiffy.gachonNoti.ui.main.setting

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.google.firebase.messaging.FirebaseMessaging
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_setting.view.*


class SettingFragment : Fragment(), SettingContract.View {
    lateinit var myView: View
    lateinit var mPresenter: SettingPresenter
    var builderIn: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_setting, container, false)
        mPresenter = SettingPresenter(this)
        mPresenter.initPresent()

        return myView
    }


    override fun changeView() {
        myView.notiSwitch.isChecked = Util.notifiSet
        themeChanger()

        myView.notiSwitch.setOnCheckedChangeListener { switch, isChecked ->
            when (isChecked) {
                false -> setOff()
                true -> {
                    if (NotificationManagerCompat.from(activity?.applicationContext!!)
                            .areNotificationsEnabled()
                    ) {
                        setOn()
                    } else {
                        switch.isChecked = false
                        setOff()

                        val tent = Intent()
                        tent.action = "android.settings.APP_NOTIFICATION_SETTINGS"

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            tent.putExtra("android.provider.extra.APP_PACKAGE", activity?.packageName)

                        } else {
                            tent.putExtra("app_package", activity?.packageName)
                            tent.putExtra("app_uid", activity?.applicationInfo?.uid)
                        }
                        startActivity(tent)
                        Toast.makeText(activity?.applicationContext, R.string.ssss, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        myView.detailSetting.setOnClickListener {
            val builder = DetailDialog(context!!)
            builder.show()
            builder.setListener(
                View.OnClickListener {
                    builder.okListen()
                    (activity as MainActivity).themeChange()
                    (activity as MainActivity).mPresenter.changeThemes()
                    builder.dismiss()
                },
                View.OnClickListener {
                    builder.dismiss()
                })

        }
        myView.bugReport.setOnClickListener {
            //            val reporter = ReportDialog(context!!, this)
//            reporter.show()
            val container = FrameLayout(context!!)
            val params =
                FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.marginStart = 30
            params.marginEnd = 30
            val editText = EditText(activity)
            editText.layoutParams = params
            container.addView(editText)
            editText.hint = "내용"
            val reporter = AlertDialog.Builder(activity)
            reporter.setTitle("버그리포트 / 개선사항")
                .setView(container)
                .setMessage("\n버그나 개선하고자 하는 사항을 입력해주세요.")
                .setPositiveButton("OK") { _, _ ->
                    checkReport(editText.text.toString())
                }
                .setNegativeButton("Cancel") { _, _ -> }
            reporter.create().show()
        }
        myView.money.setOnClickListener {
            Util.novisible = true
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://wiffy.io/gachon/donation")))
        }
        myView.maker.setOnClickListener {
            Util.novisible = true
            //startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/wiffy-io")))
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("만든이")
            builder.setMessage("박정호 - 소프트웨어학과\n(iveinvalue@gmail.com)\n\n박상현 - 소프트웨어학과\n(okpsh0033@gmail.com)")
            builder.setPositiveButton(
                "OK"
            ) { _, _ -> }
            builder.show()
        }
        myView.source.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(resources.getString(R.string.source))
            builder.setMessage(
                "Lottie\n" +
                        "com.airbnb.android:lottie:3.0.2\n\n" +
                        "TimeTable\n" +
                        "com.github.EunsilJo:TimeTable:1.0\n\n" +
                        "Jsoup\n" +
                        "org.jsoup:jsoup:1.11.3\n\n" +
                        "Library\n" +
                        "com.wang.avi:library:2.1.3\n\n" +
                        "Circleimageview\n" +
                        "de.hdodenhof:circleimageview:3.0.0"
            )
            builder.setPositiveButton(
                "OK"
            ) { _, _ -> }
            builder.show()
        }
        myView.helper.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("후원 목록")
            builder.setMessage(Util.helper)
            builder.setPositiveButton(
                "OK"
            ) { _, _ -> }
            builder.show()
        }
        myView.version.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(resources.getString(R.string.version))
            builder.setMessage(resources.getString(R.string.whatVersion))
            builder.setPositiveButton(
                "OK"
            ) { _, _ -> }
            builder.show()
        }
        myView.calling.setOnClickListener {
            val builder = ContactDialog(context!!, this)
            builder.show()
        }

    }

    private fun checkReport(str: String) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("다음과 같은 내용으로 보내시겠습니까?")
        builder.setMessage(str)
        builder.setPositiveButton(
            "OK"
        ) { _, _ ->
            executeTask2(str)
        }
        builder.setNegativeButton(
            "Cancel"
        ) { _, _ -> }
        builder.create().show()
    }

    override fun executeTask(query: String) {
        ContactAsyncTask(this, query).execute()
    }

    override fun executeTask2(query: String) {
        ReportAsyncTask(this, query).execute()
    }

    fun themeChanger() {
        val themeColorArray = arrayOf(
            intArrayOf(
                android.R.attr.state_checked
            ),
            intArrayOf(-android.R.attr.state_checked)
        )
        val color = when (Util.theme) {
            "red" -> intArrayOf(resources.getColor(R.color.red), resources.getColor(R.color.gray2))
            "green" -> intArrayOf(resources.getColor(R.color.green), resources.getColor(R.color.gray2))
            else -> intArrayOf(resources.getColor(R.color.main2Blue), resources.getColor(R.color.gray2))
        }
        val lightColor = when (Util.theme) {
            "red" -> intArrayOf(resources.getColor(R.color.lightRed), resources.getColor(R.color.lightGray))
            "green" -> intArrayOf(resources.getColor(R.color.lightGreen), resources.getColor(R.color.lightGray))
            else -> intArrayOf(resources.getColor(R.color.main2LightBlue), resources.getColor(R.color.lightGray))
        }

        myView.notiSwitch.thumbTintList = ColorStateList(themeColorArray, color)
        myView.notiSwitch.trackTintList = ColorStateList(themeColorArray, lightColor)
    }

    override fun makeToast(string: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context!!, string, Toast.LENGTH_SHORT).show()
        }
    }

    override fun builderUp() {
        Handler(Looper.getMainLooper()).post {
            builderIn = null
            builderIn = Dialog(activity!!)
            builderIn?.setContentView(R.layout.builder)
            builderIn?.setCancelable(false)
            builderIn?.setCanceledOnTouchOutside(false)
            builderIn?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            builderIn?.show()
        }
    }

    override fun builderDismissAndContactUp(list: ArrayList<ContactInformation>) {
        Handler(Looper.getMainLooper()).post {
            if (builderIn != null) {
                builderIn?.dismiss()
                if (list.isNotEmpty()) {
                    val myBuilder = ContactListDialog(activity!!, list)
                    myBuilder.show()
                } else {
                    val builder = AlertDialog.Builder(activity)
                    builder.setTitle("")
                    builder.setMessage("목록이 없습니다.")
                    builder.setPositiveButton(
                        "OK"
                    ) { _, _ -> }
                    builder.show()
                }
            }
        }
    }

    override fun builderDismiss() {
        Handler(Looper.getMainLooper()).post {
            if (builderIn != null) builderIn?.dismiss()
        }
    }

    @SuppressLint("ApplySharedPref")
    private fun setOn() {
        Util.notifiSet = true
        Util.sharedPreferences.edit().putBoolean("notiOn", true).commit()
        FirebaseMessaging.getInstance().subscribeToTopic("noti")
    }

    @SuppressLint("ApplySharedPref")
    private fun setOff() {
        Util.notifiSet = false
        Util.sharedPreferences.edit().putBoolean("notiOn", false).commit()
        FirebaseMessaging.getInstance().unsubscribeFromTopic("noti")
    }

}