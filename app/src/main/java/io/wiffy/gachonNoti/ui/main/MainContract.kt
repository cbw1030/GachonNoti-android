package io.wiffy.gachonNoti.ui.main


import androidx.fragment.app.Fragment
import com.github.eunsiljo.timetablelib.data.TimeTableData
import io.wiffy.gachonNoti.ui.main.notification.ParseList

interface MainContract {
    interface View {
        fun changeUI(mList: ArrayList<Fragment>)
        fun builderUp()
        fun builderDismiss()
        fun makeToast(str: String)
    }

    interface Presenter {
        fun initPresent()
        fun changeThemes()
    }

    interface FragmentSearcher {
        fun initUI()

        fun showLoad()
        fun dismissLoad()


        fun setTimeTable(arr:ArrayList<TimeTableData>?)
    }

    interface PresenterSearcher {
        fun initPresent()
        fun initPresentDialog(tmp: MainContract.PresenterSearchDialog)
        fun getData(yearSemester:String)
        fun isDownloaded(year: String, semester: String)
        fun showLoad()
        fun dismissLoad()
        fun error()
        fun loadRoom(roomNM:String)
        fun loadTable(str: String)
    }

    interface PresenterSearchDialog {
        fun getDataDialog(yearSemester:String)
        fun showBtn(c: Boolean)
        fun errorDialog()
        fun setSpinner(arrayList:ArrayList<String>)
        fun setlistDialog(arrayList:ArrayList<String>)
        fun requestLoad()

    }

    interface FragmentNotification {
        fun changeUI(list: ParseList)
        fun updateUI(list: ParseList)
        fun showLoad()
        fun dismissLoad()
    }

    interface PresenterNotification {
        fun resetList()
        fun uno()
        fun initPresent()
        fun load()
        fun update(data: ParseList)
        fun show()
        fun dismiss()
        fun request()
    }

    interface FragmentSetting {
        fun changeView()
    }

    interface PresenterSetting {
        fun initPresent()

    }
}