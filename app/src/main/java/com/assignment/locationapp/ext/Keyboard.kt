package com.assignment.locationapp.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

private var inputMethodManager: InputMethodManager? = null

fun Context.getInputMethodManager(): InputMethodManager? =
    inputMethodManager ?: ContextCompat.getSystemService(
        this,
        InputMethodManager::class.java
    ).also { inputMethodManager = it }

//extension functions to show and hide keyboard for a view

fun View.showKeyboard(flags: Int = InputMethodManager.SHOW_IMPLICIT): Boolean {
    requestFocus()
    return context?.getInputMethodManager()?.showSoftInput(this, flags) ?: false
}

fun View.hideKeyboard(flags: Int = 0) =
    context?.getInputMethodManager()?.hideSoftInputFromWindow(windowToken, flags) ?: false

//helper function to round off coordinates and show in a nice format
fun formatCoordinate(coordinate: String): String {
    return if (coordinate.substringAfter(".").length < 2 && coordinate.substringAfter(".") == "0") {
        coordinate.substringBefore(".")
    } else if (coordinate.substringAfter(".").length == 2 && coordinate.substringAfter(".") == "00") {
        coordinate.substringBefore(".")
    } else {
        coordinate.substringBefore(".") + "." + coordinate.toString()
            .substringAfter(".").take(2)
    }
}

