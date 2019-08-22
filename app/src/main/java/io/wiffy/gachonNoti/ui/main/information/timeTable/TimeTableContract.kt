package io.wiffy.gachonNoti.ui.main.information.timeTable

import android.content.Context
import io.wiffy.gachonNoti.model.SuperContract

interface TimeTableContract {
    abstract class View : SuperContract.SuperFragment() {
        abstract fun initView()
        abstract fun initTable(set: HashSet<String>)
        abstract fun sendContext():Context?
    }

    interface Presenter : SuperContract.WiffyObject {
        fun initPresent()
    }
}