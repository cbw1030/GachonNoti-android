package io.wiffy.gachonNoti.ui.main.information.grade

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.function.CHECK_PATTERN
import io.wiffy.gachonNoti.function.doneLogin
import io.wiffy.gachonNoti.function.getSharedItem
import io.wiffy.gachonNoti.function.getThemeButtonResource
import io.wiffy.gachonNoti.model.CreditAverage
import io.wiffy.gachonNoti.model.CreditFormal
import io.wiffy.gachonNoti.model.PatternLockDialog
import io.wiffy.gachonNoti.model.adapter.GradeAdapter
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class GradeFragment : GradeContract.View() {
    private var myView: View? = null
    lateinit var mPresenter: GradePresenter
    lateinit var adapter: GradeAdapter
    private var mInfo: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_information_grade, container, false)

        mPresenter = GradePresenter(this)
        mPresenter.initPresent()

        return myView
    }

    override fun initView() {
        changeTheme()
        mInfo?.let {
            loginInformationSetting(it)
        } ?: doneLogin(requireActivity(), context!!)
        myView?.findViewById<Button>(R.id.gradeButton)?.setOnClickListener {
            PatternLockDialog(context!!, CHECK_PATTERN) { patternCheck() }.show()
        }
        setSpinner(ArrayList<String>().apply {
            add("전체")
            try {
                for (n in getSharedItem("number", "000000000").substring(
                    0,
                    4
                ).toInt()..Calendar.getInstance().get(Calendar.YEAR)) {
                    add("${n}년")
                }
            } catch (e: Exception) {

            }

        })
        myView?.findViewById<Spinner>(R.id.spinner_semester)?.adapter =
            ArrayAdapter(
                context,
                R.layout.my_spinner,
                arrayListOf("전체", "1학기", "2학기", "여름계절학기", "겨울계절학기")
            )
        myView?.findViewById<Spinner>(R.id.spinner_grade)?.adapter =
            ArrayAdapter(
                context,
                R.layout.my_spinner,
                arrayListOf("전체", "1학년", "2학년", "3학년", "4학년")
            )

        myView?.findViewById<Button>(R.id.johaebutton)
            ?.setOnClickListener {
                GradeAsyncTask(
                    this@GradeFragment,
                    getSharedItem("number"),
                    getSpinnerValue(myView?.findViewById(R.id.spinner_year)),
                    getSpinnerValue(myView?.findViewById(R.id.spinner_semester)),
                    getSpinnerValue(myView?.findViewById(R.id.spinner_grade))
                ).execute()
            }
    }


    private fun getSpinnerValue(spinner: Spinner?): String {
        if (spinner?.selectedItem.toString() == "전체") return ""
        return when (spinner?.id) {
            R.id.spinner_year -> {
                try {
                    spinner.selectedItem.toString().trim().substring(0, 4)
                } catch (e: Exception) {
                    ""
                }
            }
            R.id.spinner_semester -> {
                try {
                    when (spinner.selectedItem.toString().trim()) {
                        "1학기" -> "10"
                        "2학기" -> "20"
                        "여름계절학기" -> "11"
                        "겨울계절학기" -> "21"
                        else -> ""
                    }
                } catch (e: Exception) {
                    ""
                }
            }
            R.id.spinner_grade -> {
                try {
                    spinner.selectedItem.toString().substring(0, 1)
                } catch (e: Exception) {
                    ""
                }
            }
            else -> {
                ""
            }
        }
    }

    override fun setSpinner(list: ArrayList<String>) {
        myView?.findViewById<Spinner>(R.id.spinner_year)?.adapter =
            ArrayAdapter(context, R.layout.my_spinner, list)
    }

    fun loginInformationSetting(info: String) {
        mInfo = info
    }

    private fun patternCheck() {
        mPresenter.patternCheck()
        setViewVisibility(true)
    }

    override fun sendContext() = context

    fun changeTheme() {
        myView?.findViewById<Button>(R.id.gradeButton)
            ?.setBackgroundResource(getThemeButtonResource())
        myView?.findViewById<Button>(R.id.johaebutton)
            ?.setBackgroundResource(getThemeButtonResource())
    }

    override fun setView(avg: CreditAverage?, list: ArrayList<CreditFormal>) {
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
        val on2 = myView?.findViewById<CardView>(R.id.johaecard)
        val off = myView?.findViewById<RelativeLayout>(R.id.gradeLayoutOff)
        if (bool) {
            on?.visibility = View.VISIBLE
            on2?.visibility = View.VISIBLE
            off?.visibility = View.GONE
        } else {
            off?.visibility = View.VISIBLE
            on2?.visibility = View.GONE
            on?.visibility = View.GONE
        }
    }

}