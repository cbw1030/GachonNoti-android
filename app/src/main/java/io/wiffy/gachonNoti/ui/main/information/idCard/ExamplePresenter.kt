package io.wiffy.gachonNoti.ui.main.information.idCard

class ExamplePresenter(val mView:ExampleContract.View):ExampleContract.Presenter {
    override fun initPresent() {
        mView.initView()
    }
}