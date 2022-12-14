package com.typicode.android.common.base

import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.typicode.android.common.arch.SingleLiveEvent
import com.typicode.android.common.extensions.hide
import com.typicode.android.common.extensions.show
import com.typicode.android.common.extensions.showConnectionErrorSnackBar
import com.typicode.android.common.ui.error.ErrorItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import splitties.toast.toast

abstract class BaseActivity(@LayoutRes layoutId: Int) : AppCompatActivity(layoutId) {

    var loadingContainer: View? = null
    var errorContainer: ErrorItem? = null
    lateinit var navController: NavController

    protected fun observeLoading(loading: MutableLiveData<Boolean>) {
        loading.observe(this) {
            if (it == true) {
                showLoading()
            } else {
                hideLoading()
            }
        }
    }

    protected fun observeErrorMessage(
        errorContainer: ErrorItem? = null,
        errorMessage: SingleLiveEvent<String?>
    ) {
        this.errorContainer = errorContainer
        errorMessage.observe(this) {
            it?.let { message ->
                if (this.errorContainer != null) {
                    lifecycleScope.launch {
                        this@BaseActivity.errorContainer?.show()
                        this@BaseActivity.errorContainer?.errorMessage = message
                        delay(2500)
                        this@BaseActivity.errorContainer?.hide()
                    }
                } else {
                    toast(message)
                }
            }
        }
    }

    protected fun observeConnectionError(
        connectionError: SingleLiveEvent<Boolean>,
        mainContainer: View,
        onRetry: () -> Unit
    ) {
        connectionError.observe(this) {
            if (it == true) {
                showConnectionErrorSnackBar(mainContainer) { onRetry.invoke() }
            }
        }
    }

    private fun showLoading() {
        loadingContainer?.show()
    }

    private fun hideLoading() {
        loadingContainer?.hide()
    }
}
