package io.wiffy.gachonNoti.ui.main.information.idCard

class IDCardPresenter(val mView: IDCardContract.View) : IDCardContract.Presenter {
    override fun initPresent() = mView.initView()
}