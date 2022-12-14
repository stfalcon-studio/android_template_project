package com.typicode.android.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.typicode.android.common.arch.SingleLiveEvent

abstract class BaseViewModel() : ViewModel() {

    val errorMessage = SingleLiveEvent<String?>()
    val isLoading = MutableLiveData<Boolean>()

    val connectionError = SingleLiveEvent<Boolean>()
    private val failedRequests = mutableListOf<() -> Unit>()

    override fun onCleared() {
        super.onCleared()
        failedRequests.clear()
    }

    open fun retry() {
        connectionError.value = false
        failedRequests.forEach {
            it.invoke()
        }
        failedRequests.clear()
    }

    fun onConnectionError(failedRequest: () -> Unit) {
        connectionError.value = true
        failedRequests.add(failedRequest)
    }
}
