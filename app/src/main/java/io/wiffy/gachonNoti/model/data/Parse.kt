package io.wiffy.gachonNoti.model.data

import java.io.Serializable

data class Parse(
    val value: String, val text: String, val data: String,
    val isNotification: Boolean, val isNew: Boolean, val isSave: Boolean, val link: String
) : Serializable