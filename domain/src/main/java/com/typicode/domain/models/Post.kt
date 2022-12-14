package com.typicode.domain.models

/**
 * @author andrii.zhumela
 * Created 13.12.2022
 */
data class Post(
    val id: Int,
    val userId: Int,
    val body: String?,
    val title: String?
)