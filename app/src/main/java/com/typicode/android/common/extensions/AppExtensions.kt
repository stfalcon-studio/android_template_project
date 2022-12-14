package com.typicode.android.common.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.typicode.android.R
import java.util.*


fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    p5: T5?,
    p6: T6?,
    block: (T1, T2, T3, T4, T5, T6) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null && p5 != null && p6 != null) block(
        p1,
        p2,
        p3,
        p4,
        p5,
        p6
    ) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    p5: T5?,
    block: (T1, T2, T3, T4, T5) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null && p5 != null) block(
        p1,
        p2,
        p3,
        p4,
        p5
    ) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    block: (T1, T2, T3, T4) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null) block(p1, p2, p3, p4) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    block: (T1, T2, T3) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
}

fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun TextView.withClickableSpan(
    text: String,
    clickablePart: String,
    textColorLink: Int? = null,
    onClickListener: () -> Unit
) {
    val span = SpannableString(text).withClickableSpan(clickablePart) {
        onClickListener.invoke()
    }
    textColorLink?.let {
        this.setLinkTextColor(ContextCompat.getColor(this.context, it))
    }
    this.movementMethod = LinkMovementMethod.getInstance()
    this.setText(span, TextView.BufferType.SPANNABLE)
    this.isClickable = true
}

/** Example
val span = SpannableString(text).withClickableSpan(clickableText) {
openWebLink(SPAN_LINK)
}
reviewEmptyStateTv.movementMethod = LinkMovementMethod.getInstance()
reviewEmptyStateTv.setText(span, TextView.BufferType.SPANNABLE)
reviewEmptyStateTv.isClickable = true
 */
fun SpannableString.withClickableSpan(
    clickablePart: String,
    onClickListener: () -> Unit
): SpannableString {
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) = onClickListener.invoke()
    }
    val clickablePartStart = indexOf(clickablePart)
    setSpan(
        clickableSpan,
        clickablePartStart,
        clickablePartStart + clickablePart.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return this
}

fun Context.openWebLink(url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent)
}

fun Context.mailTo(email: String) {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:")
    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))

    startActivity(intent)
}

fun Context.IN_DEVELOPMENT() {
    Toast.makeText(this, "IN DEVELOPMENT", Toast.LENGTH_SHORT).show()
}

fun Context.callFromDialer(number: String) {
    try {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$number")
        this.startActivity(callIntent)
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(this, "No SIM Found", Toast.LENGTH_LONG).show()
    }
}

fun Activity.showConnectionErrorSnackBar(parentContainer: View, onRetry: () -> Unit) {
    Snackbar.make(
        parentContainer, getString(R.string.error_server_api),
        Snackbar.LENGTH_INDEFINITE
    )
        .setAction(getString(R.string.all_retry).uppercase(Locale.getDefault())) {
            onRetry()
        }.show()
}

fun Activity.hideStatusBars() {
    WindowInsetsControllerCompat(window, window.decorView)
        .hide(WindowInsetsCompat.Type.statusBars())
}

fun Activity.showStatusBars() {
    WindowInsetsControllerCompat(window, window.decorView)
        .show(WindowInsetsCompat.Type.statusBars())
}

fun Fragment.showConnectionErrorSnackBar(parentContainer: View, onRetry: () -> Unit) {
    Snackbar.make(
        parentContainer, getString(R.string.error_server_api),
        Snackbar.LENGTH_INDEFINITE
    )
        .setAction(getString(R.string.all_retry).uppercase(Locale.getDefault())) {
            onRetry()
        }.show()
}
