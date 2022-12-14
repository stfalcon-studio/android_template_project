package com.typicode.android.common.extensions

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

fun View.show() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.hide() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

fun View.invisible() {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
}

fun View.applyPadding(
    start: Int? = null,
    top: Int? = null,
    end: Int? = null,
    bottom: Int? = null
) {

    setPaddingRelative(
        start ?: paddingLeft,
        top ?: paddingTop,
        end ?: paddingRight,
        bottom ?: paddingBottom
    )
}

fun View.setMargins(
    startMarginDp: Int? = null,
    topMarginDp: Int? = null,
    endMarginDp: Int? = null,
    bottomMarginDp: Int? = null
) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        startMarginDp?.run { params.marginStart = this }
        topMarginDp?.run { params.topMargin = this }
        endMarginDp?.run { params.marginEnd = this }
        bottomMarginDp?.run { params.bottomMargin = this }
        requestLayout()
        invalidate()
    }
}

fun View.showSoftKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, 0)
}

fun View.hideSoftKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.sp: Int
    get() = this * Resources.getSystem().displayMetrics.scaledDensity.toInt()

fun View.makeGoneIf(predicate: Boolean) {
    if (predicate) makeGone() else makeVisible()
}

fun View.makeGoneIf(predicate: Boolean, or: Int = View.VISIBLE) {
    if (predicate) makeGone()
    else visibility = or
}

fun View.makeInvisibleIf(predicate: Boolean) {
    if (predicate) makeInvisible() else makeVisible()
}

fun View.makeVisibleIf(predicate: Boolean, or: Int = View.GONE) {
    if (predicate) makeVisible()
    else visibility = or
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View?.fitSystemWindowsAndAdjustResize() = this?.let { view ->
    ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
        view.fitsSystemWindows = true
        val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom

        WindowInsetsCompat
            .Builder()
            .setInsets(
                WindowInsetsCompat.Type.systemBars(),
                Insets.of(0, 0, 0, bottom)
            )
            .build()
            .apply {
                ViewCompat.onApplyWindowInsets(v, this)
            }
    }
}
