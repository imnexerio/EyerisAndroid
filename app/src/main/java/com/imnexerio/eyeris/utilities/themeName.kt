package com.imnexerio.eyeris.utilities

import android.content.Context
import android.util.TypedValue
import com.imnexerio.eyeris.R

// Utility function to get themeName from the current theme
fun Context.getThemeName(): String {
    val value = TypedValue()
    theme.resolveAttribute(R.attr.themeName, value, true)
    return value.string.toString()
}