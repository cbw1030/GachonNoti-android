package io.wiffy.gachonNoti.ui.main.setting

import android.os.Handler
import android.os.Looper
import com.google.firebase.messaging.FirebaseMessaging
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.function.setSharedItem

class SettingPresenter(private val mView: SettingContract.View) : SettingContract.Presenter {
    override fun initPresent() = mView.changeView()
    override fun setOff() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("noti").addOnCompleteListener {
            if (it.isSuccessful) {
                Component.notificationSet = false
                setSharedItem("notiOn", false)
                toast("알림 OFF")
            } else {
                toast("알 수 없는 오류")
                setSharedItem("notiOn", true)
                mView.setSwitch(true)
            }
        }
    }

    override fun setOn() {
        FirebaseMessaging.getInstance().subscribeToTopic("noti").addOnCompleteListener {
            if (it.isSuccessful) {
                Component.notificationSet = true
                setSharedItem("notiOn", true)
                toast("알림 ON")
            } else {
                setSharedItem("notiOn", false)
                toast("알 수 없는 오류")
                mView.setSwitch(false)
            }

        }
    }

    override fun setAdminLogin() {
        FirebaseMessaging.getInstance().subscribeToTopic("admin").addOnSuccessListener {
            setSharedItem("ADMIN", true)
            Component.adminMode = true
            toast("ADMIN MODE ON")
        }
    }

    override fun setAdminLogout() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("admin").addOnSuccessListener {
            setSharedItem("ADMIN", false)
            Component.adminMode = false
            toast("ADMIN MODE OFF")
        }
    }

    fun toast(str: String) = Handler(Looper.getMainLooper()).post { mView.toast(str) }
}