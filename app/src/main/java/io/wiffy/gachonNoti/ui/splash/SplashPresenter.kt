package io.wiffy.gachonNoti.ui.splash

import android.os.Handler
import android.os.Looper


class SplashPresenter(private val mView:SplashContract.View):SplashContract.Presenter {

    override fun initPresent() {
        mView.changeUI()
    }

    override fun move() {
        Handler(Looper.getMainLooper()).postDelayed({
            mView.moveToMain()
        },1234)
    }
}