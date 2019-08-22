package io.wiffy.gachonNoti.model

import android.content.Context
import android.os.Bundle
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import io.wiffy.gachonNoti.R
import kotlinx.android.synthetic.main.dialog_pattern_lock.*

class PatternLockDialog(context: Context, private val state: Int) : SuperContract.SuperDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.dialog_pattern_lock)

        pattern_lock_view.addPatternLockListener(object : PatternLockViewListener {
            override fun onComplete(pattern: MutableList<PatternLockView.Dot>?) {
                console("onComplete")
            }

            override fun onCleared() {
                console("onCleared")
            }

            override fun onStarted() {
                console("onStarted")
            }

            override fun onProgress(progressPattern: MutableList<PatternLockView.Dot>?) {
                console("onProgress")
            }
        })

        super.onCreate(savedInstanceState)
    }
}