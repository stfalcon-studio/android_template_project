package com.typicode.domain.exceptions

import com.typicode.domain.models.errors.ApiError


class NotSupportedVersionException(val apiError: ApiError?): Throwable()