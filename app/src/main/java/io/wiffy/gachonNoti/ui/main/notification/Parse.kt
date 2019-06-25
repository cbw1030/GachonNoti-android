package io.wiffy.gachonNoti.ui.main.notification

import java.io.Serializable

data class Parse(val value:String,val text:String,val data:String,
                 val isNoti:Boolean,val isNew:Boolean,val isSave:Boolean):Serializable