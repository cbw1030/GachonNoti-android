package io.wiffy.gachonNoti.model

import java.io.Serializable

data class Parse(
    val value: String, val text: String, val data: String,
    val isNoti: Boolean, val isNew: Boolean, val isSave: Boolean, val link: String
) : Serializable