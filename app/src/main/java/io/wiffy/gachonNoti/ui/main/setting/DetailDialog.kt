package io.wiffy.gachonNoti.ui.main.setting

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import de.hdodenhof.circleimageview.CircleImageView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import kotlinx.android.synthetic.main.activity_detailsetting.*


class DetailDialog(context: Context) : Dialog(context) {
    lateinit var list: ArrayList<CircleImageView>
    var index = -1
    var preIndex = -1
    var setNum = 0

    @SuppressLint("ApplySharedPref")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailsetting)

        setNum = Util.seek
        setText.text = setNum.toString()

        toLeft.setOnClickListener {
            if (setNum > 10) {
                setNum -= 5
                setText.text = setNum.toString()
            }
        }

        toRight.setOnClickListener {
            if (setNum < 100) {
                setNum += 5
                setText.text = setNum.toString()
            }
        }

        OK.setBackgroundColor(
            ContextCompat.getColor(
                context, when (Util.theme) {
                    "red" -> R.color.red
                    "green" -> R.color.green
                    else -> R.color.main2Blue
                }
            )

        )
        Cancel.setBackgroundColor(
            ContextCompat.getColor(
                context, when (Util.theme) {
                    "red" -> R.color.red
                    "green" -> R.color.green
                    else -> R.color.main2Blue
                }
            )
        )

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
                OK.setBackgroundColor(
                    ContextCompat.getColor(
                        context, when (x) {
                            1 -> R.color.red
                            2 -> R.color.green
                            else -> R.color.main2Blue
                        }
                    )
                )
                Cancel.setBackgroundColor(
                    ContextCompat.getColor(
                        context, when (x) {
                            1 -> R.color.red
                            2 -> R.color.green
                            else -> R.color.main2Blue
                        }
                    )
                )
            }
        }

    }

    fun setListener(positiveListener: View.OnClickListener, negativeListener: View.OnClickListener) {
        OK.setOnClickListener(positiveListener)
        Cancel.setOnClickListener(negativeListener)
    }

    @SuppressLint("ApplySharedPref")
    fun okListen() {
        Util.seek = setNum
        Util.theme = when (index) {
            1 -> "red"
            2 -> "green"
            else -> "default"
        }
        Util.sharedPreferences.edit().putInt("seek",Util.seek).commit()
        Util.sharedPreferences.edit().putString("theme", Util.theme).commit()
    }
}