package io.wiffy.gachonNoti.ui.main.setting

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import androidx.core.app.NotificationManagerCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import de.hdodenhof.circleimageview.CircleImageView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.function.*
import io.wiffy.gachonNoti.model.ContactInformation
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.ui.main.MainActivity
import io.wiffy.gachonNoti.ui.main.setting.administrator.AdministratorDialog
import io.wiffy.gachonNoti.ui.main.setting.contact.ContactAsyncTask
import io.wiffy.gachonNoti.ui.main.setting.contact.ContactDialog
import io.wiffy.gachonNoti.ui.main.setting.contact.ContactListDialog
import io.wiffy.gachonNoti.ui.main.setting.login.LoginDialog
import kotlinx.android.synthetic.main.fragment_setting.view.*
import kotlinx.android.synthetic.main.fragment_setting.view.defaultColor
import kotlinx.android.synthetic.main.fragment_setting.view.greenColor
import kotlinx.android.synthetic.main.fragment_setting.view.redColor


class SettingFragment : SettingContract.View() {
    private val myBorder = 5
    lateinit var myView: View
    lateinit var mPresenter: SettingPresenter
    lateinit var list: ArrayList<CircleImageView>
    private var secretCount = 0
    private var index = 0
    private var adminCode = StringBuilder()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        patternVisibility()
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
        myView.darkMode.isChecked = Component.darkTheme
        themeChanger()
        myView.campustext.text =
            if (Component.campus) {
                "글로벌"
            } else {
                "메디컬"
            }
        myView.notiSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (!isNetworkConnected(context!!)) {
                myView.notiSwitch.isChecked = isChecked.xor(true)
                toast("인터넷 연결 오류")
            } else
                when (isChecked) {
                    false -> mPresenter.setOff()
                    true -> {
                        if (NotificationManagerCompat.from(activity?.applicationContext!!)
                                .areNotificationsEnabled()
                        ) {
                            mPresenter.setOn()
                        } else {
                            mPresenter.setOff()

                            val tent = Intent().apply {
                                action = "android.settings.APP_NOTIFICATION_SETTINGS"
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                tent.putExtra(
                                    "android.provider.extra.APP_PACKAGE",
                                    activity?.packageName
                                )

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
        myView.darkMode.setOnCheckedChangeListener { it, isChecked ->
            MaterialAlertDialogBuilder(context).apply {
                setTitle("경고")
                setMessage("어플리케이션이 재시작됩니다.")
                setPositiveButton("ㅇㅋ") { _, _ ->
                    setSharedItem("dark", isChecked)
                    restartApp(context)
                }
                setNegativeButton("그건 좀..") { _, _ ->
                    it.isChecked = isChecked.xor(true)
                }
            }.show()
        }
        myView.patternsetting.setOnClickListener {
            MainActivity.mView.changePattern()
        }
        myView.detailSetting.setOnClickListener {
            LoginDialog(context!!).show()
        }
        myView.bugReport.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://open.kakao.com/o/gE49yGCb")
                )
            )
        }
        myView.money.setOnClickListener {
            Component.noneVisible = true
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://wiffy.io/gachon/donation")))
        }
        myView.maker.setOnClickListener {
            Component.noneVisible = true
            MaterialAlertDialogBuilder(activity).apply {
                setTitle("만든이")
                setMessage("박정호 - 소프트웨어학과\n(iveinvalue@gmail.com)\n\n박상현 - 소프트웨어학과\n(okpsh0033@gmail.com)")
                setPositiveButton(
                    "OK"
                ) { _, _ -> }
            }.show()
        }
        myView.source.setOnClickListener {
            MaterialAlertDialogBuilder(activity).apply {
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
                            "info.hoang8f:android-segmented:1.0.6\n\n" +
                            "PatternLockView\n" +
                            "com.andrognito.patternlockview:patternlockview:1.0.0\n\n" +
                            "material-dialogs\n" +
                            "com.afollestad.material-dialogs:core:3.1.0\n" +
                            "com.afollestad.material-dialogs:input:3.1.0"
                )
                setPositiveButton(
                    "OK"
                ) { _, _ -> }
            }.show()
        }
        myView.secretText.setOnClickListener {
            if (secretCount == 4) {
                MaterialAlertDialogBuilder(activity).apply {
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
            MaterialAlertDialogBuilder(activity).apply {
                setTitle("캠퍼스 설정")
                setSingleChoiceItems(
                    arrayOf("글로벌", "메디컬"), if (Component.campus) {
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
            MaterialAlertDialogBuilder(activity).apply {
                setTitle("후원 목록")
                setMessage(Component.helper)
                setPositiveButton(
                    "OK"
                ) { _, _ -> }
            }.show()
        }
        myView.version.setOnClickListener {
            MaterialAlertDialogBuilder(activity).apply {
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

    fun themeChanger() = arrayOf(
        intArrayOf(
            android.R.attr.state_checked
        ),
        intArrayOf(-android.R.attr.state_checked)
    ).also {
        myView.notiSwitch.apply {
            thumbTintList =
                ColorStateList(
                    it,
                    intArrayOf(
                        resources.getColor(getThemeColor()),
                        resources.getColor(R.color.gray2)
                    )
                )
            trackTintList = ColorStateList(
                it, intArrayOf(
                    resources.getColor(
                        getThemeLightColor()
                    ), resources.getColor(R.color.lightGray)
                )
            )
        }
        myView.darkMode.apply {
            thumbTintList =
                ColorStateList(
                    it,
                    intArrayOf(
                        resources.getColor(getThemeColor()),
                        resources.getColor(R.color.gray2)
                    )
                )
            trackTintList = ColorStateList(
                it, intArrayOf(
                    resources.getColor(
                        getThemeLightColor()
                    ), resources.getColor(R.color.lightGray)
                )
            )
        }

    }

    override fun builderUp() {
        Handler(Looper.getMainLooper()).post {
            Component.getBuilder()?.show()
        }
    }

    override fun builderDismissAndContactUp(list: ArrayList<ContactInformation>) {
        Handler(Looper.getMainLooper()).post {
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
            Component.getBuilder()?.dismiss()
        }
    }

    override fun builderDismiss() = Handler(Looper.getMainLooper()).post {
        Component.getBuilder()?.dismiss()
    }

    private fun adminCode(id: Int) {
        when (id) {
            0 -> {
                adminCode.append("0")
                if (adminCode.toString() == "012120" && Component.adminMode) {
                    MaterialDialog(activity!!).show {
                        title(text = "Administrator Option")
                        message(text = "Input Secret Code")
                        input(hint = "Code") { _, text ->
                            when (text.toString().trim()) {
                                "훔쳐보지마세요" -> {
                                    AdministratorDialog(context).show()
                                }
                                else -> {
                                    toast("Error")
                                    dismiss()
                                }
                            }
                        }
                        positiveButton(text = "OK")
                        negativeButton(text = "Cancel")
                    }
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

    override fun adminLogout() = mPresenter.setAdminLogout()

    override fun adminLogin() = mPresenter.setAdminLogin()

    fun patternVisibility() =
        if (Component.isLogin) {
            View.VISIBLE
        } else {
            View.GONE
        }.run {
            myView.patternsettingA.visibility = this
            myView.patternsettingB.visibility = this
        }

    override fun setSwitch(bool: Boolean) {
        Handler(Looper.getMainLooper()).post {
            myView.notiSwitch.isChecked = bool
        }
    }
}