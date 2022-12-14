package com.typicode.data.api.models.errors

import com.google.gson.annotations.SerializedName

class InvalidEntityViolations(
    @SerializedName("title")
    val title: String?,
    @SerializedName("detail")
    val detail: String?,
    @SerializedName("violations")
    val violations: List<Violation>?
)
