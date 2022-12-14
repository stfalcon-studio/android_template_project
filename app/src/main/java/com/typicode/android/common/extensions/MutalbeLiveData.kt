package com.typicode.android.common.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyValueChange() {
    value = value
}

fun <T> MutableLiveData<T>.withDefault(defaultValue: T) = this.apply {
    value = defaultValue
}

/**
 * Observe on [MutableLiveData] & return only not null values
 */
fun <T> MutableLiveData<T>.safeObserve(owner: LifecycleOwner, listener: (T) -> Unit) {
    observe(owner) { value ->
        value?.let { listener(it) }
    }
}
