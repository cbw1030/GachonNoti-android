package io.wiffy.gachonNoti.ui.main.searcher

import io.wiffy.gachonNoti.model.Util

class ClassTime {
    private val week = Array(5) { ClassTimePerDay() }

    fun getAllday(): Array<ClassTimePerDay> = week
    fun getMonday(): ClassTimePerDay = week[Util.MONDAY]
    fun getTuesday(): ClassTimePerDay = week[Util.TUESDAY]
    fun getWednesday(): ClassTimePerDay = week[Util.WEDNESDAY]
    fun getThursday(): ClassTimePerDay = week[Util.THURSDAY]
    fun getFriday(): ClassTimePerDay = week[Util.FRIDAY]


}