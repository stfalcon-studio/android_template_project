package com.typicode.data.api.models.errors

import com.google.gson.annotations.SerializedName
import com.typicode.domain.models.errors.ApiError
import com.typicode.domain.models.errors.Violations

open class ApiErrorResponse(
    @SerializedName("error")
    val error: String? = null,

    @SerializedName("errorDescription")
    val errorDescription: String? = null,

    @SerializedName("violations")
    val invalidEntityViolations: InvalidEntityViolations? = null

) {
    fun toDomain(): ApiError {

        return ApiError(
            errorDescription,
            error,
            invalidEntityViolations?.violations?.map { Violations(it.propertyPath, it.title) }
        )
    }
}
