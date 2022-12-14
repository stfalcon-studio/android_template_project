package com.typicode.android.features

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.Timber
import com.typicode.android.common.base.BaseViewModel
import com.typicode.android.common.extensions.notifyValueChange
import com.typicode.android.common.extensions.withDefault
import com.typicode.domain.models.User
import com.typicode.domain.usecases.base.ResultCallbacks
import com.typicode.domain.usecases.users.SubscribeToUsersUseCase

/**
 * @author andrii.zhumela
 * Created 13.12.2022
 */
class MainVM constructor(
    private val subscribeUsersUseCase: SubscribeToUsersUseCase
) : BaseViewModel() {

    val userList = MutableLiveData<MutableList<User>>().withDefault(mutableListOf())

    init {
        subscribeToUsers()
    }

    private fun subscribeToUsers() {
        subscribeUsersUseCase.invoke(
            uiDispatcher = viewModelScope,
            result = ResultCallbacks(
                onSuccess = { user ->
                    userList.value?.add(user)
                    userList.notifyValueChange()
                },
                onError = {
                    errorMessage.value = it.apiError?.toString()
                    Timber.e(it)
                },
                onLoading = {
                    isLoading.value = it
                },
                onConnectionError = {
                    Timber.e(it)
                    onConnectionError { subscribeToUsers() }
                },
                onUnexpectedError = {
                    errorMessage.value = it.localizedMessage
                    Timber.e(it)
                }
            )
        )
    }
}