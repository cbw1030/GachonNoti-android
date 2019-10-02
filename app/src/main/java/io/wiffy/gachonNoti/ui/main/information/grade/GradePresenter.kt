package io.wiffy.gachonNoti.ui.main.information.grade

import io.wiffy.gachonNoti.utils.getSharedItem

class GradePresenter(val mView: GradeContract.View) : GradeContract.Presenter {
    override fun initPresent() = mView.initView()
    override fun patternCheck() {
        GradeAsyncTask(mView, getSharedItem("number")).execute()
    }
}