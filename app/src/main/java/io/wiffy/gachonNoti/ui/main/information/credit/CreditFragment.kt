package io.wiffy.gachonNoti.ui.main.information.credit

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.function.doneLogin
import io.wiffy.gachonNoti.function.getSharedItem
import io.wiffy.gachonNoti.model.CreditInformation
import io.wiffy.gachonNoti.model.adapter.CreditAdapter

class CreditFragment : CreditContract.View() {
    private var myView: View? = null
    private lateinit var mPresenter: CreditPresenter
    private var mInfo: String? = null
    private lateinit var adapter: CreditAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_information_credit, container, false)

        mPresenter = CreditPresenter(this)
        mPresenter.initPresent()

        return myView
    }

    override fun initView() {
        changeTheme()
        mInfo?.let {
            loginInformationSetting(it)
        } ?: doneLogin(requireActivity(), context!!)
    }

    @SuppressLint("SetTextI18n")
    override fun initList(list: ArrayList<CreditInformation>, cmd: String) {
        Handler(Looper.getMainLooper()).post {
            myView?.findViewById<TextView>(R.id.credityou)?.text =
                "$cmd/${getSharedItem<String>("department")}/${getSharedItem<String>("name")}/${getSharedItem<String>(
                    "number"
                )}"
            adapter = CreditAdapter(list)
            myView?.findViewById<RecyclerView>(R.id.creditRecycler2)?.run {
                this.adapter = this@CreditFragment.adapter
                layoutManager = LinearLayoutManager(activity)
            }
        }
    }

    fun loginInformationSetting(info: String) {
        mInfo = info
        myView?.let {
            if (info.length > 6) {
                mPresenter.setLogin(info)
            }
        }
    }

    fun changeTheme() {

    }

    override fun sendContext() = context
}