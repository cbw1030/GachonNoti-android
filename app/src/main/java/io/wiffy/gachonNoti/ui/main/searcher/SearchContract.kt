package io.wiffy.gachonNoti.ui.main.searcher

import com.github.eunsiljo.timetablelib.data.TimeTableData

interface SearchContract {

    interface View {
        fun initUI()
        fun showLoad()
        fun dismissLoad()
        fun setTimeTable(arr: ArrayList<TimeTableData>?, name: String)
    }

    interface Presenter {
        fun initPresent()
        fun initPresentDialog(tmp: DialogPresenter)
        fun getData(yearSemester: String)
        fun isDownloaded(year: String, semester: String)
        fun showLoad()
        fun dismissLoad()
        fun error()
        fun loadRoom(roomNM: String)
        fun loadTable(str: String)
    }

    interface DialogPresenter {
        fun cate_invi()
        fun getDataDialog(yearSemester: String)
        fun showBtn(c: Boolean)
        fun errorDialog()
        fun setSpinner(arrayList: ArrayList<String>)
        fun setListDialog(arrayList: ArrayList<String>)
        fun requestLoad()
        fun dismissSelf()
    }


}