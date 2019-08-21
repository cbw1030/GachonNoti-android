package io.wiffy.gachonNoti.ui.main.information.timeTable

import io.wiffy.gachonNoti.model.SuperContract

interface TimeTableContract {
    abstract class View : SuperContract.SuperFragment() {
        abstract fun initView()
    }

    interface Presenter : SuperContract.WiffyObject {
        fun initPresent()
    }
}