package io.wiffy.gachonNoti.model

import java.io.Serializable

class ParseList : Serializable {

    private val list = ArrayList<Parse>()

    fun add(x: Parse) = list.add(x)

    fun get(index: Int): Parse = list[index]

    fun size(): Int = list.size

    fun clear() {
        list.clear()
    }
}