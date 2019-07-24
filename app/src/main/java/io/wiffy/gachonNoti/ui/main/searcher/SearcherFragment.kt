package io.wiffy.gachonNoti.ui.main.searcher


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.github.eunsiljo.timetablelib.view.TimeTableView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_searcher.view.*
import com.github.eunsiljo.timetablelib.data.TimeTableData
import io.wiffy.gachonNoti.model.Util
import kotlin.collections.ArrayList

class SearcherFragment : Fragment(), SearchContract.View {
    lateinit var myView: View
    lateinit var mPresenter: SearcherPresenter
//    lateinit var fabOpen: Animation
//    lateinit var fabClose: Animation

    var builder: SearchDialog? = null
//    private var isFABOpen = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_searcher, container, false)
        mPresenter = SearcherPresenter(this)
        mPresenter.initPresent()
        return myView
    }

    override fun initUI() {

//        Glide.with(this).load(R.drawable.outline_autorenew_white_24dp).into(myView.subFab1)
        Glide.with(this).load(R.drawable.search).into(myView.fab)

//        fabOpen = AnimationUtils.loadAnimation(context, R.anim.fab_open)
//        fabClose = AnimationUtils.loadAnimation(context, R.anim.fab_close)
        myView.fab.setOnClickListener {
//            floatingButtonControl()
            builder = SearchDialog(context!!, this, mPresenter)
            builder?.show()
        }
/*
        myView.fab.setOnLongClickListener {
            anim()
            true
        }
        */
        /*
        myView.subFab1.setOnClickListener {
            resetDialog()
            anim()
        }
        */
//        myView.superParentOfSearcher.setOnTouchListener { _, _ ->
//            if (isFABOpen) {
//                anim()
//                true
//            } else false
//        }
//        myView.card2.setOnTouchListener { _, _ ->
//            if (isFABOpen) {
//                anim()
//                true
//            } else false
//        }
        themeChanger()
        setTimeTable(null, "")
    }

    @SuppressLint("ApplySharedPref")
    fun resetDialog() {
        val mBuilder = AlertDialog.Builder(context)
        mBuilder.setMessage("저장된 데이터를 재설정 할까요?")
        mBuilder.setPositiveButton(
            "OK"
        ) { _, _ ->
            val year = Util.YEAR
            val semester = Util.SEMESTER.toString()
            for (x in arrayOf("global", "medical")) {
                Util.sharedPreferences.edit().apply {
                    putString("$year-$semester-1-$x", "<nodata>")
                    putString("$year-$semester-2-$x", "<nodata>")
                    putString("$year-$semester-3-$x", "<nodata>")
                    putString("$year-$semester-4-$x", "<nodata>")
                }.commit()

            }
            builder = SearchDialog(context!!, this, mPresenter)
            builder?.show()
        }
        mBuilder.show()
    }

//    override fun floatingButtonControl() =
//        if (isFABOpen) {
//            Handler(Looper.getMainLooper()).post {
//                anim()
//            }
//            true
//        } else false


//    private fun anim() {
//        isFABOpen = if (isFABOpen) {
//            myView.subFab1.startAnimation(fabClose)
//            myView.subFab1.visibility = View.GONE
//            false
//        } else {
//            myView.subFab1.startAnimation(fabOpen)
//            myView.subFab1.visibility = View.VISIBLE
//            true
//        }
//
//    }

    fun themeChanger() {
        myView.fab.backgroundTintList = resources.getColorStateList(
            when (Util.theme) {
                "red" -> R.color.red
                "green" -> R.color.green
                else -> R.color.main2Blue
            }
        )
//        myView.subFab1.backgroundTintList = resources.getColorStateList(
//            when (Util.theme) {
//                "red" -> R.color.deepRed
//                "green" -> R.color.deepGreen
//                else -> R.color.main2DeepBlue
//            }
//        )
        myView.semester.setTextColor(
            resources.getColor(
                when (Util.theme) {
                    "red" -> R.color.red
                    "green" -> R.color.green
                    else -> R.color.main2Blue
                }
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun setTimeTable(arr: ArrayList<TimeTableData>?, name: String) {
        myView.timetable.setStartHour(9)
        myView.timetable.setShowHeader(true)
        myView.timetable.setTableMode(TimeTableView.TableMode.SHORT)

        if (arr == null) {
            myView.showtu.visibility = View.VISIBLE
        } else {
            myView.timetable.setOnTimeItemClickListener { _, _, data ->
                Toast.makeText(activity, data.time.title, Toast.LENGTH_SHORT).show()
            }
            myView.timetable.setTimeTable(0, arr)
            myView.tableName.text = name
            myView.semester.text = "${Util.YEAR}년도 ${when (Util.SEMESTER) {
                1 -> "1"
                2 -> "2"
                3 -> "여름"
                else -> "겨울"
            }}학기 [${if (Util.campus) {
                "G"
            } else {
                "M"
            }}]"
            themeChanger()
            myView.tables.visibility = View.VISIBLE
            myView.showtu.visibility = View.GONE
        }

    }


    override fun showLoad() {
        (activity as MainActivity).builderUp()
    }

    override fun dismissLoad() {
        (activity as MainActivity).builderDismiss()
    }


}