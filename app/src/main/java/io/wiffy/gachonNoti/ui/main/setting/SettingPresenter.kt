package io.wiffy.gachonNoti.ui.main.setting

class SettingPresenter(private val mView: SettingContract.View) : SettingContract.Presenter {
    override fun initPresent() {
        mView.changeView()
    }
}