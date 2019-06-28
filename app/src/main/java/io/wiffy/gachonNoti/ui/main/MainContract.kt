package io.wiffy.gachonNoti.ui.main


import androidx.fragment.app.Fragment
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
        fun getdataDialog(yearsemester:String)
        fun showBtn(c: Boolean)
        fun showLoad()
        fun dismissLoad()
        fun errorDialog()
        fun setSpinner(arrayList:ArrayList<String>)
    }

    interface PresenterSearcher {
        fun initPresent()
        fun getdata(yearsemester:String)
        fun isdownloaded(year: String, semester: String)
        fun showLoad()
        fun dismissLoad()
        fun error()
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