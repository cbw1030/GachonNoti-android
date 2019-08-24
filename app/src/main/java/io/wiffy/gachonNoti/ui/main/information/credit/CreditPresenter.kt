package io.wiffy.gachonNoti.ui.main.information.credit

class CreditPresenter(val mView: CreditContract.View) : CreditContract.Presenter {
    override fun initPresent() = mView.initView()
}