package io.wiffy.gachonNoti.ui.main.setting

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
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
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessaging
import de.hdodenhof.circleimageview.CircleImageView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.func.*
import io.wiffy.gachonNoti.model.ContactInformation
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.ui.main.MainActivity
import io.wiffy.gachonNoti.ui.main.setting.contact.ContactAsyncTask
import io.wiffy.gachonNoti.ui.main.setting.contact.ContactDialog
import io.wiffy.gachonNoti.ui.main.setting.contact.ContactListDialog
import io.wiffy.gachonNoti.ui.main.setting.login.LoginDialog
import io.wiffy.gachonNoti.ui.main.setting.report.ReportAsyncTask
import kotlinx.android.synthetic.main.fragment_setting.view.*
import kotlinx.android.synthetic.main.fragment_setting.view.defaultColor
import kotlinx.android.synthetic.main.fragment_setting.view.greenColor
import kotlinx.android.synthetic.main.fragment_setting.view.redColor


class SettingFragment : SettingContract.View() {
    private val myBorder = 5
    lateinit var myView: View
    lateinit var mPresenter: SettingPresenter
    lateinit var list: ArrayList<CircleImageView>
    private var builderIn: Dialog? = null
    private var secretCount = 0
    private var index = 0
    private var adminCode = StringBuilder()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_setting, container, false)
        mPresenter = SettingPresenter(this)
        mPresenter.initPresent()

        return myView
    }

    @SuppressLint("ApplySharedPref")
    override fun changeView() {
        index = when (Component.theme) {
            "red" -> 1
            "green" -> 2
            else -> 0
        }
        adminView()
        list = ArrayList<CircleImageView>().apply {
            add(myView.defaultColor)
            add(myView.redColor)
            add(myView.greenColor)
            this[index].borderWidth = myBorder
            for (x in 0 until size) {
                this[x].setOnClickListener {
                    for (v in 0 until size) {
                        if (x == v)
                            this[v].borderWidth = myBorder
                        else
                            this[v].borderWidth = 0
                    }
                    index = x
                    settingColor(x)
                    adminCode(x)
                }
            }
        }
        myView.notiSwitch.isChecked = Component.notificationSet
        themeChanger()
        myView.campustext.text =
            if (Component.campus) {
                "글로벌"
            } else {
                "메디컬"
            }
        myView.notiSwitch.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                false -> setOff()
                true -> {
                    if (NotificationManagerCompat.from(activity?.applicationContext!!)
                            .areNotificationsEnabled()
                    ) {
                        setOn()
                    } else {
                        setOff()

                        val tent = Intent().apply {
                            action = "android.settings.APP_NOTIFICATION_SETTINGS"
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            tent.putExtra("android.provider.extra.APP_PACKAGE", activity?.packageName)

                        } else {
                            tent.putExtra("app_package", activity?.packageName)
                            tent.putExtra("app_uid", activity?.applicationInfo?.uid)
                        }
                        startActivity(tent)
                        toast(R.string.ssss)
                    }
                }
            }
        }
        myView.detailSetting.setOnClickListener {
            LoginDialog(context!!).show()
        }
        myView.bugReport.setOnClickListener {
            val container = FrameLayout(context!!)
            val params =
                FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.marginStart = 40
            params.marginEnd = 40
            val editText = EditText(activity)
            editText.layoutParams = params
            container.addView(editText)
            editText.hint = "내용"
            AlertDialog.Builder(activity).apply {
                setTitle("버그리포트 / 개선사항")
                setView(container)
                setMessage("\n버그나 개선하고자 하는 사항을 입력해주세요.")
                setPositiveButton("OK") { _, _ ->
                    editText.text.toString().apply {
                        if (isNotBlank()) checkReport(this)
                    }
                }
                setNegativeButton("Cancel") { _, _ -> }
            }.create().show()
        }
        myView.money.setOnClickListener {
            Component.noneVisible = true
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://wiffy.io/gachon/donation")))
        }
        myView.adminOption.setOnClickListener {
            AlertDialog.Builder(activity).apply {
                setTitle("관리자 옵션")
                setMessage("관리자 옵션을 추가해주십시요.")
                setPositiveButton(
                    "OK"
                ) { _, _ -> }
            }.show()
        }
        myView.maker.setOnClickListener {
            Component.noneVisible = true
            AlertDialog.Builder(activity).apply {
                setTitle("만든이")
                setMessage("박정호 - 소프트웨어학과\n(iveinvalue@gmail.com)\n\n박상현 - 소프트웨어학과\n(okpsh0033@gmail.com)")
                setPositiveButton(
                    "OK"
                ) { _, _ -> }
            }.show()
        }
        myView.source.setOnClickListener {
            AlertDialog.Builder(activity).apply {
                setTitle(resources.getString(R.string.source))
                setMessage(
                    "Lottie\n" +
                            "com.airbnb.android:lottie:3.0.2\n\n" +
                            "TimeTable\n" +
                            "com.github.EunsilJo:TimeTable:1.0\n\n" +
                            "Jsoup\n" +
                            "org.jsoup:jsoup:1.11.3\n\n" +
                            "Library\n" +
                            "com.wang.avi:library:2.1.3\n\n" +
                            "Circleimageview\n" +
                            "de.hdodenhof:circleimageview:3.0.0\n\n" +
                            "Glide\n" +
                            "com.github.bumptech.glide:glide:4.9.0\n\n" +
                            "QRCode\n" +
                            "com.journeyapps:zxing-android-embedded:3.5.0\n\n" +
                            "android-segmented-control\n" +
                            "info.hoang8f:android-segmented:1.0.6"
                )
                setPositiveButton(
                    "OK"
                ) { _, _ -> }
            }.show()
        }
        myView.secretText.setOnClickListener {
            if (secretCount == 4) {
                AlertDialog.Builder(activity).apply {
                    setTitle("Build Information")
                    setMessage("BRAND:${Build.BRAND}\nMODEL:${Build.MODEL}\nVERSION:${Build.VERSION.RELEASE}\nSDK:${Build.VERSION.SDK_INT}\nRELEASE:${Component.version}\nMAC:${getMACAddress()}")

                    setPositiveButton(
                        "OK"
                    ) { _, _ -> }
                }.show()
                secretCount = 0
            } else {
                secretCount += 1
            }
        }
        myView.campusSetting.setOnClickListener {
            val item = arrayOf("글로벌", "메디컬")
            AlertDialog.Builder(activity).apply {
                setTitle("캠퍼스 설정")
                setSingleChoiceItems(
                    item, if (Component.campus) {
                        0
                    } else {
                        1
                    }
                ) { dialog, num ->
                    changeCampus(
                        when (num) {
                            0 -> true
                            else -> false
                        }
                    )
                    dialog.dismiss()
                }
                setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
            }.create().show()
        }
        myView.helper.setOnClickListener {
            AlertDialog.Builder(activity).apply {
                setTitle("후원 목록")
                setMessage(Component.helper)
                setPositiveButton(
                    "OK"
                ) { _, _ -> }
            }.show()
        }
        myView.version.setOnClickListener {
            AlertDialog.Builder(activity).apply {
                setTitle(resources.getString(R.string.version))
                setMessage(Component.version)
                setPositiveButton(
                    "OK"
                ) { _, _ -> }
            }.show()
        }
        myView.calling.setOnClickListener {
            ContactDialog(context!!, this).show()
        }

    }

    @SuppressLint("ApplySharedPref")
    override fun changeCampus(bool: Boolean) {
        myView.campustext.text =
            if (bool) {
                "글로벌"
            } else {
                "메디컬"
            }
        setSharedItem("campus", bool)
        Component.campus = bool
    }

    private fun checkReport(str: String) = AlertDialog.Builder(activity).apply {
        setTitle("다음과 같은 내용으로 보내시겠습니까?")
        setMessage(str)
        setPositiveButton(
            "OK"
        ) { _, _ ->
            executeTask2(str)
        }
        setNegativeButton(
            "Cancel"
        ) { _, _ -> }
    }.create().show()


    @SuppressLint("ApplySharedPref")
    fun settingColor(int: Int) {
        Component.theme = when (int) {
            2 -> "green"
            1 -> "red"
            else -> "default"
        }
        setSharedItem("theme", Component.theme)
        themeChanger()
        (activity as MainActivity).themeChange()
        (activity as MainActivity).mPresenter.changeThemes()
    }

    override fun executeTask(query: String, query2: Boolean, query3: Boolean) {
        ContactAsyncTask(this, query, query2, query3).execute()
    }

    override fun executeTask2(query: String) {
        ReportAsyncTask(this, query).execute()
    }

    fun themeChanger() = arrayOf(
        intArrayOf(
            android.R.attr.state_checked
        ),
        intArrayOf(-android.R.attr.state_checked)
    ).let {
        myView.notiSwitch.thumbTintList =
            ColorStateList(it, intArrayOf(resources.getColor(getThemeColor()), resources.getColor(R.color.gray2)))
        myView.notiSwitch.trackTintList = ColorStateList(
            it, intArrayOf(
                resources.getColor(
                    getThemeLightColor()
                ), resources.getColor(R.color.lightGray)
            )
        )
    }


    override fun builderUp() {
        Handler(Looper.getMainLooper()).post {
            builderIn = null
            builderIn = Dialog(activity!!).apply {
                setContentView(R.layout.builder)
                setCancelable(false)
                setCanceledOnTouchOutside(false)
                this.window?.setBackgroundDrawableResource(android.R.color.transparent)
            }
            builderIn?.show()
        }
    }

    override fun builderDismissAndContactUp(list: ArrayList<ContactInformation>) {
        Handler(Looper.getMainLooper()).post {
            builderIn?.let {
                it.dismiss()
                if (list.isNotEmpty()) {
                    ContactListDialog(activity!!, list).show()
                } else {
                    AlertDialog.Builder(activity).apply {
                        setTitle("")
                        setMessage("목록이 없습니다.")
                        setPositiveButton(
                            "OK"
                        ) { _, _ -> }
                    }.show()
                }
            }
        }
    }

    override fun builderDismiss() = Handler(Looper.getMainLooper()).post {
        builderIn?.dismiss()
    }

    private fun adminCode(id: Int) {
        when (id) {
            0 -> {
                adminCode.append("0")
                if (adminCode.toString() == "01210") {
                    toast("add action")
                    adminCode.clear()
                } else {
                    adminCode.clear()
                    adminCode.append("0")
                }
            }
            1 -> {
                adminCode.append("1")
            }
            2 -> {
                adminCode.append("2")
            }
        }
    }

    private fun adminView() {
        if (Component.adminMode) {
            myView.adminOption.visibility = View.VISIBLE
            myView.adminBar.visibility = View.VISIBLE
        } else {
            myView.adminOption.visibility = View.GONE
            myView.adminBar.visibility = View.GONE
        }
    }

    override fun adminLogout() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("admin").addOnSuccessListener {
            setSharedItem("ADMIN", false)
            Component.adminMode = false
            adminView()
            toast("ADMIN MODE OFF")
        }
    }

    override fun adminLogin() {
        FirebaseMessaging.getInstance().subscribeToTopic("admin").addOnSuccessListener {
            setSharedItem("ADMIN", true)
            Component.adminMode = true
            adminView()
            toast("ADMIN MODE ON")
        }
    }

    @SuppressLint("ApplySharedPref")
    private fun setOn() = FirebaseMessaging.getInstance().subscribeToTopic("noti").addOnCompleteListener {
        myView.notiSwitch.isChecked = if (it.isSuccessful) {
            Component.notificationSet = true
            setSharedItem("notiOn", true)
            true
        } else {
            console("알 수 없는 오류")
            false
        }

    }


    @SuppressLint("ApplySharedPref")
    private fun setOff() = FirebaseMessaging.getInstance().unsubscribeFromTopic("noti").addOnCompleteListener {
        myView.notiSwitch.isChecked = if (it.isSuccessful) {
            Component.notificationSet = false
            setSharedItem("notiOn", false)
            false
        } else {
            console("알 수 없는 오류")
            true
        }
    }

}