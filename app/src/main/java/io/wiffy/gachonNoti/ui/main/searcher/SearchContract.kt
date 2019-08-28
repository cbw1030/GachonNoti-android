package io.wiffy.gachonNoti.ui.main.searcher

import android.content.Context
import com.github.eunsiljo.timetablelib.data.TimeTableData
import io.wiffy.gachonNoti.model.SuperContract

interface SearchContract {

    abstract class View : SuperContract.SuperFragment() {
        abstract fun initUI()
        abstract fun setTimeTable(arr: ArrayList<TimeTableData>?, name: String)
        abstract fun searcherVisible(bool: Boolean)
    }

    abstract class DialogPresenter(context: Context) : SuperContract.SuperDialog(context) {
        abstract fun categoryInvisible()
        abstract fun getDataDialog(yearSemester: String)
        abstract fun showBtn(c: Boolean)
        abstract fun errorDialog()
        abstract fun setSpinner(arrayList: ArrayList<String>)
        abstract fun setListDialog(arrayList: ArrayList<String>)
        abstract fun requestLoad()
        abstract fun dismissSelf()
    }

    interface Presenter : SuperContract.WiffyObject {
        fun initPresent()
        fun initPresentDialog(tmp: DialogPresenter)
        fun getData(yearSemester: String)
        fun isDownloaded(year: String, semester: String)
        fun showLoad()
        fun dismissLoad()
        fun error()
        fun loadRoom(roomNM: String)
        fun loadTable(str: String)
        fun resetData()
    }

}