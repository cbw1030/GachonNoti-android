package io.wiffy.gachonNoti.model

import android.content.SharedPreferences


@Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
object Component {

    lateinit var sharedPreferences: SharedPreferences

    var adminMode = false

    var YEAR = "2019"

    var isLogin = false

    var surfing = false

    var SEMESTER = 1

    var firstBoot = true

    var looper = true

    var version = "1.0.0"

    var notificationSet = true

    var delay = 100L

    var state = 0

    var helper = "인터넷 연결을 확인해주세요."

    var theme = "default"

    var myRoom = "강의실"

    var noneVisible = false

    var campus = true

    lateinit var timeTableSet: HashSet<String>

}
