package io.wiffy.gachonNoti.function

import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.`object`.Component

fun getDarkColor1() = R.color.myDark1

fun getDarkColor2() = R.color.myDark2

fun getDarkLightColor() = R.color.myDarkLight



fun getThemeColor(mTheme: String?) = when (mTheme) {
    "red" -> {
        R.color.red
    }
    "green" -> {
        R.color.green
    }
    else -> {
        R.color.main2Blue
    }
}

fun getThemeColor() = when (Component.theme) {
    "red" -> {
        R.color.red
    }
    "green" -> {
        R.color.green
    }
    else -> {
        R.color.main2Blue
    }
}

fun getThemeButtonResource(mTheme: String?) = when (mTheme) {
    "red" -> {
        R.drawable.dialog_button_red
    }
    "green" -> {
        R.drawable.dialog_button_green
    }
    else -> {
        R.drawable.dialog_button_default
    }
}

fun getThemeButtonResource() = if (Component.darkTheme) {
    R.drawable.dialog_button_dark
} else {
    when (Component.theme) {
        "red" -> {
            R.drawable.dialog_button_red
        }
        "green" -> {
            R.drawable.dialog_button_green
        }
        else -> {
            R.drawable.dialog_button_default
        }
    }
}

fun getThemeDeepColor() = when (Component.theme) {
    "red" -> {
        R.color.deepRed
    }
    "green" -> {
        R.color.deepGreen
    }
    else -> {
        R.color.main2DeepBlue
    }
}

fun getThemeLightColor() = when (Component.theme) {
    "red" -> R.color.lightRed
    "green" -> R.color.lightGreen
    else -> R.color.main2LightBlue
}

fun getThemeTransColor() = when (Component.theme) {
    "red" -> R.color.transRed
    "green" -> R.color.transGreen
    else -> R.color.main2TransBlue
}

fun getThemeMyTransColor() = when (Component.theme) {
    "red" -> R.color.myTrans_red
    "green" -> R.color.myTrans_green
    else -> R.color.myTrans_def
}
