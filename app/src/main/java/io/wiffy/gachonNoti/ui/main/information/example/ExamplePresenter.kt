package io.wiffy.gachonNoti.ui.main.information.example

class ExamplePresenter(val mView:ExampleContract.View):ExampleContract.Presenter {
    override fun initPresent() {
        mView.initView()
    }
}