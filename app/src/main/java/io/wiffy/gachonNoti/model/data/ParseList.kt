package io.wiffy.gachonNoti.model.data

import java.io.Serializable

class ParseList : Serializable {

    private val list = ArrayList<Parse>()

    fun add(x: Parse) = list.add(x)

    fun get(index: Int): Parse = list[index]

    fun size(): Int = list.size

    fun isEmpty():Boolean=list.isEmpty()

    fun clear() {
        list.clear()
    }
}