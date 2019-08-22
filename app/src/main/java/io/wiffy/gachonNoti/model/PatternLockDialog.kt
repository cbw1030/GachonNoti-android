package io.wiffy.gachonNoti.model

import android.content.Context
import android.os.Bundle
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.andrognito.patternlockview.utils.PatternLockUtils
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.func.*
import kotlinx.android.synthetic.main.dialog_pattern_lock.*

class PatternLockDialog(context: Context, mState: Int) : SuperContract.SuperDialog(context) {

    private var state = mState
    private var prePattern: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.dialog_pattern_lock)
        gogobebe.setCardBackgroundColor(context.resources.getColor(getThemeTransColor()))

        pattern_lock_view.run {
            layoutParams = layoutParams.apply {
                height = Component.deviceWidth
                width = Component.deviceWidth
            }
            addPatternLockListener(object : PatternLockViewListener {
                override fun onComplete(pattern: MutableList<PatternLockView.Dot>?) {
                    val mPattern = PatternLockUtils.patternToString(this@run, pattern)
                    clearPattern()
                    when (state) {
                        SET_PATTERN -> {
                            if (mPattern.length >= 4) {
                                prePattern = mPattern
                                bulabula.text = "패턴을 재확인합니다."
                                state = SET_PATTERN2
                            } else {
                                bulabula.text = "패턴이 너무 짧습니다."
                            }

                        }
                        SET_PATTERN2 -> {
                            if (mPattern == prePattern) {
                                setSharedItem("pattern", mPattern)
                                bulabula.text = "설정 완료"
                                dismiss()
                            } else {
                                bulabula.text = "패턴이 틀립니다. 다시 설정해주세요."
                                state = SET_PATTERN
                            }
                        }
                        CHECK_PATTERN -> {
                            val mP = getSharedItem<String>("pattern")
                        }
                    }
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
            invalidate()
        }

        super.onCreate(savedInstanceState)
    }
}