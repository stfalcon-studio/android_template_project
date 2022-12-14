package com.typicode.android.common.ui.error

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.typicode.android.R
import com.typicode.android.common.extensions.hide

/**
 * @author andrii.zhumela
 * Created 13.12.2022
 */
class ErrorItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val errorMessageTv: TextView

    var errorMessage: String? = null
        set(value) {
            field = value
            errorMessageTv.text = value
        }

    init {
        val rootView = LayoutInflater.from(context)
            .inflate(R.layout.part_view_error, this, true)
        errorMessageTv = rootView.findViewById(R.id.errorMessageTv)
        this.hide()
    }
}