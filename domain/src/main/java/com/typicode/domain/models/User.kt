package com.typicode.domain.models

/**
 * @author andrii.zhumela
 * Created 13.12.2022
 */
data class User(
    val userId: Int,
    val albumId: Int,
    val name: String?,
    val thumbnailUrl: String?,
    val url: String?,
    val postCount: Int?
)