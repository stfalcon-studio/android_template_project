package com.typicode.domain.usecases.base

import com.typicode.domain.exceptions.ApiErrorException
import com.typicode.domain.exceptions.ConnectionErrorException
import kotlinx.coroutines.*

abstract class BaseUseCase<RESULT, PARAMS> : UseCase<RESULT, PARAMS> {

    operator fun invoke(
        uiDispatcher: CoroutineScope,
        result: ResultCallbacks<RESULT>,
        params: PARAMS? = null
    ): Job {
        return uiDispatcher.launch {
            withContext(Dispatchers.Main) {
                result.onLoading?.invoke(true)
                try {
                    val resultOfWork = remoteWork(params)
                    result.onSuccess?.invoke(resultOfWork)
                    result.onLoading?.invoke(false)
                } catch (e: Throwable) {
                    when (e) {
                        is ApiErrorException -> result.onError?.invoke(e)
                        is ConnectionErrorException -> result.onConnectionError?.invoke(e)
                        else -> result.onUnexpectedError?.invoke(e)
                    }
                    result.onLoading?.invoke(false)
                }
            }
        }
    }

}

interface UseCase<RESULT, PARAMS> {
    suspend fun remoteWork(params: PARAMS?): RESULT
}

