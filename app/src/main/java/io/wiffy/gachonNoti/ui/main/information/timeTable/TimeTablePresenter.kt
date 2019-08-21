package io.wiffy.gachonNoti.ui.main.information.timeTable

class TimeTablePresenter(val mView: TimeTableContract.View) : TimeTableContract.Presenter {
    override fun initPresent() = mView.initView()
}