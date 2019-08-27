package io.wiffy.gachonNoti.model

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.andrognito.patternlockview.utils.PatternLockUtils
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.func.*
import io.wiffy.gachonNoti.ui.main.MainActivity
import kotlinx.android.synthetic.main.dialog_pattern_lock.*

class PatternLockDialog(context: Context, mState: Int) :
    SuperContract.SuperDialog(context) {

    private var state = mState
    private var prePattern: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.dialog_pattern_lock)

        window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        pattern_lock_view.run {
            if (getSharedItem<String>("pattern").length < 4) {
                state = SET_PATTERN
            }

            bulabula.text = when (state) {
                CHANGE_PATTERN -> "현재 패턴을 확인합니다."
                SET_PATTERN -> "패턴을 설정합니다.."
                CHECK_PATTERN -> "패턴을 확인합니다."
                else -> "알 수 없음"
            }

            addPatternLockListener(object : PatternLockViewListener {
                override fun onComplete(pattern: MutableList<PatternLockView.Dot>?) {
                    val mPattern = PatternLockUtils.patternToString(this@run, pattern)
                    clearPattern()
                    when (state) {
                        CHANGE_PATTERN -> {
                            if (getSharedItem<String>("pattern") == mPattern) {
                                bulabula.text = "새로운 패턴을 입력해주세요."
                                state = SET_PATTERN
                            } else {
                                bulabula.text = "패턴을 확인해주세요."
                            }
                        }
                        SET_PATTERN -> {
                            if (mPattern.length >= 4) {
                                if (getSharedItem<String>("pattern") == mPattern) {
                                    bulabula.text = "이전 패턴과 같습니다."
                                } else {
                                    prePattern = mPattern
                                    bulabula.text = "패턴을 재확인합니다."
                                    state = SET_PATTERN2
                                }
                            } else {
                                bulabula.text = "패턴이 너무 짧습니다."
                            }
                        }
                        SET_PATTERN2 -> {
                            if (mPattern == prePattern) {
                                setSharedItem("pattern", mPattern)
                                bulabula.text = "설정 완료"
                                toast("패턴을 설정했습니다.")
                                dismiss()
                            } else {
                                bulabula.text = "패턴이 틀립니다. 다시 설정해주세요."
                                state = SET_PATTERN
                            }
                        }
                        CHECK_PATTERN -> {
                            if (getSharedItem<String>("pattern") == mPattern) {
                                MainActivity.mView.checkPattern()
                                dismiss()
                            } else {
                                bulabula.text = "패턴이 틀립니다. 다시 입력해주세요."
                            }
                        }
                    }
                }

                override fun onCleared() {
                }

                override fun onStarted() {
                }

                override fun onProgress(progressPattern: MutableList<PatternLockView.Dot>?) {
                }
            })
            invalidate()
        }

        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() = dismiss()
}