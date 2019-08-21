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

    var noneVisible = false

    var campus = true

    lateinit var timeTableSet: HashSet<String>

    var titles = arrayListOf(
        Pair("공지사항", false),
        Pair("내 정보", false),
        Pair("강의실", true),
        Pair("설정", false),
        Pair("가천알림이", false)
    )

}
