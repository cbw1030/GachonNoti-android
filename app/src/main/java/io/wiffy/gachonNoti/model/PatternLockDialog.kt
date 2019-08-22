package io.wiffy.gachonNoti.model

import android.content.Context
import android.os.Bundle
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.andrognito.patternlockview.utils.PatternLockUtils
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.func.getThemeTransColor
import kotlinx.android.synthetic.main.dialog_pattern_lock.*

class PatternLockDialog(context: Context, private val state: Int) : SuperContract.SuperDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.dialog_pattern_lock)
        gogobebe.run {
            setCardBackgroundColor(context.resources.getColor(getThemeTransColor()))
            layoutParams = layoutParams.apply {
                height = Component.deviceWidth
                width = Component.deviceWidth
            }
        }
        pattern_lock_view.run {

            layoutParams = layoutParams.apply {
                height = Component.deviceWidth - 3
                width = Component.deviceWidth - 3
            }


            addPatternLockListener(object : PatternLockViewListener {
                override fun onComplete(pattern: MutableList<PatternLockView.Dot>?) {
                    console("Pattern Completed: ${PatternLockUtils.patternToString(this@run, pattern)}")
                }

                override fun onCleared() {
                    console("onCleared")
                }

                override fun onStarted() {
                    console("Pattern Drawing Started")
                }

                override fun onProgress(progressPattern: MutableList<PatternLockView.Dot>?) {
                    console("Pattern Progress: ${PatternLockUtils.patternToString(this@run, progressPattern)}")
                }
            })

        }

        super.onCreate(savedInstanceState)
    }
}