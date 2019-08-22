package io.wiffy.gachonNoti.ui.main.information.grade

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.func.CHECK_PATTERN
import io.wiffy.gachonNoti.func.getSharedItem
import io.wiffy.gachonNoti.func.getThemeButtonResource
import io.wiffy.gachonNoti.model.CreditAverage
import io.wiffy.gachonNoti.model.CreditFormal
import io.wiffy.gachonNoti.model.PatternLockDialog
import io.wiffy.gachonNoti.model.adapter.GradeAdapter

class GradeFragment : GradeContract.View() {
    var myView: View? = null
    lateinit var mPresenter: GradePresenter
    lateinit var adapter: GradeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_information_grade, container, false)

        mPresenter = GradePresenter(this)
        mPresenter.initPresent()

        return myView
    }

    override fun initView() {
        changeTheme()
        myView?.findViewById<Button>(R.id.gradeButton)?.setOnClickListener {
            PatternLockDialog(context!!, CHECK_PATTERN).show()
        }
    }

    fun patternCheck() {
        GradeAsyncTask(this@GradeFragment, getSharedItem("number")).execute()
        setViewVisibility(true)
    }

    override fun sendContext() = context

    fun changeTheme() {
        myView?.findViewById<Button>(R.id.gradeButton)?.setBackgroundResource(getThemeButtonResource())
    }

    override fun setView(avg: CreditAverage?, list: ArrayList<CreditFormal>) {
        //set View plz
        myView?.findViewById<TextView>(R.id.creditAverage)?.text =
            Html.fromHtml(avg.toString())
        adapter = GradeAdapter(list)
        myView?.findViewById<RecyclerView>(R.id.creditRecycler)?.run {
            this.adapter = this@GradeFragment.adapter
            layoutManager = LinearLayoutManager(activity)
        }
        setViewVisibility(true)
    }

    fun setViewVisibility(bool: Boolean) {
        val on = myView?.findViewById<RelativeLayout>(R.id.gradeLayoutOn)
        val off = myView?.findViewById<RelativeLayout>(R.id.gradeLayoutOff)
        if (bool) {
            on?.visibility = View.VISIBLE
            off?.visibility = View.GONE
        } else {
            off?.visibility = View.VISIBLE
            on?.visibility = View.GONE
        }
    }


}