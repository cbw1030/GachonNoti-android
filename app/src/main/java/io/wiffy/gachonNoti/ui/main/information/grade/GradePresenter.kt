package io.wiffy.gachonNoti.ui.main.information.grade

class GradePresenter(val mView: GradeContract.View) : GradeContract.Presenter {
    override fun initPresent() = mView.initView()
}