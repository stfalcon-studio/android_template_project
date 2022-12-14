package com.typicode.data.extensions

import com.google.gson.Gson
import com.typicode.data.api.models.errors.ApiErrorResponse
import com.typicode.domain.exceptions.ApiErrorException
import com.typicode.domain.exceptions.ConnectionErrorException
import com.typicode.domain.exceptions.NotSupportedVersionException
import com.typicode.domain.exceptions.UnauthorizedException
import com.typicode.domain.models.errors.ApiError
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

fun Throwable.mapToApiErrors(handleUnAuthException: Boolean = false): Throwable {
    when (this) {
        is HttpException -> {
            return if (handleUnAuthException && (this.code() == 401 || this.code() == 403)) {
                UnauthorizedException()
            } else if (this.code() == 500) {
                ApiErrorException(apiError = ApiError(errorDescription = "Woops! Something happened with server"))
            } else if (this.code() == 404) {
                ConnectionErrorException()
            } else {
                val errorResponse = try {
                    Gson().fromJson(
                        this.response()?.errorBody()?.string(),
                        ApiErrorResponse::class.java
                    )
                } catch (e: Exception) {
                    ApiErrorResponse(errorDescription = "Api error")
                }
                if (errorResponse.error == "version_not_supported") {
                    NotSupportedVersionException(errorResponse?.toDomain())
                } else {
                    ApiErrorException(errorResponse?.toDomain())
                }
            }
        }
        is UnknownHostException,
        is SocketTimeoutException,
        is ConnectException,
        is TimeoutException -> return ConnectionErrorException()
        else -> return this
    }
}

fun Response<*>.handleApiError() {
    if (!isSuccessful) {
        val errorResponse = try {
            Gson().fromJson(
                this.errorBody()?.string(),
                ApiErrorResponse::class.java
            )
        } catch (e: Exception) {
            ApiErrorResponse(errorDescription = "Api error")
        }
        throw ApiErrorException(errorResponse?.toDomain())
    }
}
