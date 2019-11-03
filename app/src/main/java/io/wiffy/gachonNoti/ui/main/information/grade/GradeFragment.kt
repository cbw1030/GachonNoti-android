package io.wiffy.gachonNoti.ui.main.information.grade

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.utils.CHECK_PATTERN
import io.wiffy.gachonNoti.utils.doneLogin
import io.wiffy.gachonNoti.utils.getSharedItem
import io.wiffy.gachonNoti.utils.getThemeButtonResource
import io.wiffy.gachonNoti.model.CreditAverage
import io.wiffy.gachonNoti.model.CreditFormal
import io.wiffy.gachonNoti.model.customView.PatternLockDialog
import io.wiffy.gachonNoti.model.adapter.GradeAdapter
import kotlinx.android.synthetic.main.fragment_information_grade.view.*
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
        myView?.gradeButton?.setOnClickListener {
            PatternLockDialog(
                context!!,
                CHECK_PATTERN
            ) { patternCheck() }.show()
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
        myView?.spinner_semester?.adapter =
            ArrayAdapter(
                context!!,
                R.layout.my_spinner,
                arrayListOf("전체", "1학기", "2학기", "여름계절학기", "겨울계절학기")
            )
        myView?.spinner_grade?.adapter =
            ArrayAdapter(
                context!!,
                R.layout.my_spinner,
                arrayListOf("전체", "1학년", "2학년", "3학년", "4학년")
            )

        myView?.johaebutton
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
        myView?.spinner_year?.adapter =
            ArrayAdapter(context!!, R.layout.my_spinner, list)
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
        myView?.gradeButton
            ?.setBackgroundResource(getThemeButtonResource())
        myView?.johaebutton
            ?.setBackgroundResource(getThemeButtonResource())
    }

    override fun setView(avg: CreditAverage?, list: ArrayList<CreditFormal>) {
        myView?.creditAverage?.text =
            Html.fromHtml(avg.toString())
        adapter = GradeAdapter(list)
        myView?.creditRecycler?.run {
            this.adapter = this@GradeFragment.adapter
            layoutManager = LinearLayoutManager(activity)
        }
        setViewVisibility(true)
    }

    fun setViewVisibility(bool: Boolean) {
        if (bool) {
            myView?.gradeLayoutOn?.visibility = View.VISIBLE
            myView?.johaecard?.visibility = View.VISIBLE
            myView?.gradeLayoutOff?.visibility = View.GONE
        } else {
            myView?.gradeLayoutOff?.visibility = View.VISIBLE
            myView?.gradeLayoutOn?.visibility = View.GONE
            myView?.johaecard?.visibility = View.GONE
        }
    }
}