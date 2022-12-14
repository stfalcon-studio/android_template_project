package com.typicode.domain.exceptions

import com.typicode.domain.models.errors.ApiError


class ApiErrorException(val apiError: ApiError?) : Throwable()
