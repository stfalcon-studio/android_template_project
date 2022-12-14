package com.typicode.domain.models.errors


open class ApiError(
    val errorDescription: String?,
    val error: String? = null,
    val entityViolations: List<Violations>? = null
) {
    val hasConflict = error == "conflict_target_resource_update"

    override fun toString(): String {
        return entityViolations?.firstOrNull()?.title ?: errorDescription ?: error ?: "Api error"
    }

    fun getByKey(propertyKey: String): Violations? {
        return entityViolations?.find { it.propertyPath == propertyKey }
    }
}

open class Violations(
    val propertyPath: String?,
    val title: String?
) {
    override fun toString(): String {
        return title ?: "Api error"
    }
}
