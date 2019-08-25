package io.wiffy.gachonNoti.ui.main.setting

import android.os.Handler
import android.os.Looper
import com.google.firebase.messaging.FirebaseMessaging
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.func.setSharedItem

class SettingPresenter(private val mView: SettingContract.View) : SettingContract.Presenter {
    override fun initPresent() = mView.changeView()
    override fun setOff() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("noti").addOnCompleteListener {
            mView.setSwitch(
                if (it.isSuccessful) {
                    Component.notificationSet = false
                    setSharedItem("notiOn", false)
                    false
                } else {
                    console("알 수 없는 오류")
                    setSharedItem("notiOn", true)
                    true
                }
            )
        }
    }

    override fun setOn() {
        FirebaseMessaging.getInstance().subscribeToTopic("noti").addOnCompleteListener {
            mView.setSwitch(
                if (it.isSuccessful) {
                    Component.notificationSet = true
                    setSharedItem("notiOn", true)
                    true
                } else {
                    setSharedItem("notiOn", false)
                    console("알 수 없는 오류")
                    false
                }
            )
        }
    }

    override fun setAdminLogin() {
        FirebaseMessaging.getInstance().subscribeToTopic("admin").addOnSuccessListener {
            setSharedItem("ADMIN", true)
            Component.adminMode = true
            mView.adminView()
            toast("ADMIN MODE ON")
        }
    }

    override fun setAdminLogout() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("admin").addOnSuccessListener {
            setSharedItem("ADMIN", false)
            Component.adminMode = false
            mView.adminView()
            toast("ADMIN MODE OFF")
        }
    }

    fun toast(str: String) = Handler(Looper.getMainLooper()).post { mView.toast(str) }
}