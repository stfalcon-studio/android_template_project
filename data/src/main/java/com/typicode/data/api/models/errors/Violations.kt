package com.typicode.data.api.models.errors

import com.google.gson.annotations.SerializedName

class Violation(
    @SerializedName("propertyPath")
    val propertyPath: String,
    @SerializedName("title")
    val title: String
)
