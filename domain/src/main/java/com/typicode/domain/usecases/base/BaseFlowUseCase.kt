package com.typicode.domain.usecases.base

import com.typicode.domain.exceptions.ApiErrorException
import com.typicode.domain.exceptions.ConnectionErrorException
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

abstract class BaseFlowUseCase<RESULT, PARAMS> : FlowUseCase<RESULT, PARAMS> {

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
                    resultOfWork.collect {
                        result.onSuccess?.invoke(it)
                    }
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

interface FlowUseCase<RESULT, PARAMS> {
    suspend fun remoteWork(params: PARAMS?): Flow<RESULT>
}
