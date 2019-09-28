package io.wiffy.gachonNoti.function

fun getWordByKorean(str: String, first: String, second: String) = str.last().let {
    if (it < 0xAC00.toChar() || it > 0xD7A3.toChar()) {
        str
    } else {
        str + if ((it - 0xAC00).toInt() % 28 > 0) {
            first
        } else {
            second
        }
    }
}

fun StringBuilder.appendf(string:String)
{
    append("${string}\n")
}