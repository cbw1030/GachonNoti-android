package io.wiffy.gachonNoti.model

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class MyViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private var mEnabled = true

    override fun onTouchEvent(ev: MotionEvent?) = if (mEnabled) super.onTouchEvent(ev)
    else false

    override fun onInterceptTouchEvent(ev: MotionEvent?) = if (mEnabled) super.onInterceptTouchEvent(ev)
    else false

    fun setPagingEnabled(bl: Boolean) {
        this.mEnabled = bl
    }
}