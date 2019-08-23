package io.wiffy.gachonNoti.ui.main.information.credit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.CreditInformation

class CreditFragment : CreditContract.View() {
    var myView: View? = null
    lateinit var mPresenter: CreditPresenter
    private var mInfo: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_information_credit, container, false)

        mPresenter = CreditPresenter(this)
        mPresenter.initPresent()

        return myView
    }

    override fun initView() {
        changeTheme()
        mInfo?.let {
            loginInformationSetting(it)
        }
    }

    override fun initList(list: ArrayList<CreditInformation>) {
            //List까지 가져옴
    }

    fun loginInformationSetting(info: String) {
        mInfo = info
        myView?.let {
            if (info.length > 6) {
                CreditAsyncTask(this, info).execute()
            }
        }
    }

    fun changeTheme() {

    }

    override fun sendContext() = context
}