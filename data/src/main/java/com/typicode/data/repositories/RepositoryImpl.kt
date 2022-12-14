package com.typicode.data.repositories

import com.typicode.data.api.models.responses.PostResponse
import com.typicode.data.api.models.responses.UserResponse
import com.typicode.data.api.services.CommonService
import com.typicode.data.extensions.mapToApiErrors
import com.typicode.domain.models.Post
import com.typicode.domain.models.User
import com.typicode.domain.repositories.Repository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow

class RepositoryImpl(
    private val networkService: CommonService
) : Repository {

    override suspend fun getUsers(): List<User> {
        try {
            return networkService.getUsers().map { UserResponse.mapToDomain(it) }
        } catch (e: Throwable) {
            throw e.mapToApiErrors()
        }
    }

    override suspend fun getUsersFlow(): Flow<User> = callbackFlow {
        try {
            networkService.getUsers().map { userResponse ->
                this.channel.send(UserResponse.mapToDomain(userResponse))
            }

            awaitClose { close() }
        } catch (e: Throwable) {
            throw e.mapToApiErrors()
        }
    }

    /*override suspend fun getUsersFlow(): Flow<User> = callbackFlow {
        try {
            val posts = getPost()
            networkService.getUsers().map { userResponse ->
                val postCount = posts.filter { it.userId == userResponse.userId }.size
                val user = UserResponse.mapToDomain(userResponse).copy(postCount = postCount)
                this.channel.send(user)
            }

            awaitClose { close() }
        } catch (e: Throwable) {
            throw e.mapToApiErrors()
        }
    }*/

    override suspend fun getPost(): List<Post> {
        try {
            return networkService.getPosts().map { PostResponse.mapToDomain(it) }
        } catch (e: Throwable) {
            throw e.mapToApiErrors()
        }
    }
}
