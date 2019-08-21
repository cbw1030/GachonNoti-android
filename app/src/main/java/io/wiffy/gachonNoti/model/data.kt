package io.wiffy.gachonNoti.model

import java.io.Serializable

data class Parse(
    val value: String, val text: String, val data: String,
    val isNotification: Boolean, val isNew: Boolean, val isSave: Boolean, val link: String
) : Serializable

class ParseList : ArrayList<Parse>(), Serializable

data class ClassDataInformation(val name: String, val time: String, val room: String)

data class ContactInformation(val dept: String, val name: String, val tel: String)

data class StudentInformation(
    val name: String?,
    val number: String?,
    val id: String?,
    val password: String?,
    val department: String?,
    val imageURL: String?
) {
    override fun toString() = "name:$name\nnumber:$number\ndepartment:$department"
}

data class TimeTableInformation(
    val day: String,
    val subject: String,
    val professor: String,
    val place: String,
    val start: String,
    val end: String
) {
    val information = "$day:$subject:$professor:$place:$start:$end"

    override fun toString() =
        "\nday : $day \n" +
                "subject : $subject \n" +
                "professor : $professor \n" +
                "place : $place \n" +
                "time : $start - $end"
}