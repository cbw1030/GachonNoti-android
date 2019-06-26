package io.wiffy.gachonNoti.ui.main.setting

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.google.firebase.messaging.FirebaseMessaging
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.MainContract
import kotlinx.android.synthetic.main.fragment_setting.view.*


class SettingFragment : Fragment(), MainContract.FragmentSetting {
    lateinit var myView: View
    lateinit var mPresenter: SettingPresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_setting, container, false)
        mPresenter = SettingPresenter(this)
        mPresenter.initPresent()

        return myView
    }


    override fun changeView() {
        myView.notiSwitch.isChecked = Util.notifiSet

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
        myView.money.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://wiffy.io/gachon/donation")))
        }
        myView.maker.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/wiffy-io")))
        }
        myView.source.setOnClickListener {
            val builder = AlertDialog.Builder(activity, R.style.light_dialog)
            builder.setTitle(resources.getString(R.string.source))
            builder.setMessage(resources.getString(R.string.open))
            builder.setPositiveButton(
                "OK"
            ) { _, _ -> }
            builder.show()
        }
        myView.helper.setOnClickListener {
            val builder = AlertDialog.Builder(activity, R.style.light_dialog)
            builder.setTitle("후원 목록")
            builder.setMessage(Util.helper)
            builder.setPositiveButton(
                "OK"
            ) { _, _ -> }
            builder.show()
        }
        myView.version.setOnClickListener {
            val builder = AlertDialog.Builder(activity, R.style.light_dialog)
            builder.setTitle(resources.getString(R.string.version))
            builder.setMessage(resources.getString(R.string.whatVersion))
            builder.setPositiveButton(
                "OK"
            ) { _, _ -> }
            builder.show()
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