package io.wiffy.gachonNoti.ui.main.setting

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import de.hdodenhof.circleimageview.CircleImageView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import kotlinx.android.synthetic.main.activity_detailsetting.*

class DetailSetting : AppCompatActivity() {
    lateinit var list: ArrayList<CircleImageView>
    var index = -1
    var preIndex = -1
    @SuppressLint("ApplySharedPref")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailsetting)

        index = when (Util.theme) {
            "red" -> 1
            "green" -> 2
            else -> 0
        }
        preIndex = index

        list = ArrayList()
        list.add(defaultColor)
        list.add(redColor)
        list.add(greenColor)
        list[index].borderWidth = 4
        for (x in 0 until list.size) {
            list[x].setOnClickListener {
                for (v in 0 until list.size) {
                    if (x == v)
                        list[v].borderWidth = 4
                    else
                        list[v].borderWidth = 0
                }
                index = x
            }
        }

        OK.setOnClickListener {
            Util.theme = when(index)
            {
                1->"red"
                2->"green"
                else->"default"
            }

            Util.sharedPreferences.edit().putString("theme",Util.theme).commit()
            setResult(Util.RESULT_SETTING_CHANGED)
            finish()
        }
        Cancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    override fun onBackPressed() {

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean =true
}