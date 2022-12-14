package com.typicode.domain.repositories

import com.typicode.domain.models.Post
import com.typicode.domain.models.User
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getUsers(): List<User>

    suspend fun getUsersFlow(): Flow<User>

    suspend fun getPost(): List<Post>
}
