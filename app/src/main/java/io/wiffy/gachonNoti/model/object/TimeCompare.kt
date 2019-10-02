package io.wiffy.gachonNoti.model.`object`

import com.github.eunsiljo.timetablelib.data.TimeData

object TimeCompare : Comparator<TimeData<Any?>?> {
    override fun compare(o1: TimeData<Any?>?, o2: TimeData<Any?>?): Int {
        return o1!!.startMills.compareTo(o2!!.startMills)
    }
}