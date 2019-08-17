package io.wiffy.gachonNoti.ui.main.searcher

import android.app.Dialog
import android.content.Context
import androidx.fragment.app.Fragment
import com.github.eunsiljo.timetablelib.data.TimeTableData

interface SearchContract {

    abstract class View : Fragment() {
        abstract fun initUI()
        abstract fun showLoad()
        abstract fun dismissLoad(): Boolean?
        abstract fun setTimeTable(arr: ArrayList<TimeTableData>?, name: String)
    }

    abstract class DialogPresenter(context: Context) : Dialog(context) {
        abstract fun categoryInvisible()
        abstract fun getDataDialog(yearSemester: String)
        abstract fun showBtn(c: Boolean)
        abstract fun errorDialog()
        abstract fun setSpinner(arrayList: ArrayList<String>)
        abstract fun setListDialog(arrayList: ArrayList<String>)
        abstract fun requestLoad()
        abstract fun dismissSelf()
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
        fun resetData()
    }

}