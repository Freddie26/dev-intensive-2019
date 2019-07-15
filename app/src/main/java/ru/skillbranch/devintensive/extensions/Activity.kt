package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard(): Unit
{
    // Check if no view has focus:
    val view = this.currentFocus
    if (view != null)
    {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.isKeyboardOpen(): Boolean
{
    fun Activity.getRootView(): View = findViewById(android.R.id.content)

    fun Context.convertDpToPx(dp: Float): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        this.resources.displayMetrics
    )

    val visibleBounds = Rect()
    val view = getRootView()
    view.getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = view.height - visibleBounds.height()
    val marginOfError = Math.round(this.convertDpToPx(50F))
    return heightDiff > marginOfError
}

fun Activity.isKeyboardClosed() = !isKeyboardOpen()