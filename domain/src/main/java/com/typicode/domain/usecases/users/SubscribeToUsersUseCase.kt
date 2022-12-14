package com.typicode.domain.usecases.users

import com.typicode.domain.models.User
import com.typicode.domain.repositories.Repository
import com.typicode.domain.usecases.base.BaseFlowUseCase
import com.typicode.domain.usecases.base.BaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

/**
 * @author andrii.zhumela
 * Created 13.12.2022
 */
class SubscribeToUsersUseCase constructor(
    private val repository: Repository
) : BaseFlowUseCase<User, Unit>() {

    override suspend fun remoteWork(params: Unit?): Flow<User> {
        return withContext(Dispatchers.IO) {
            flow {
                val posts = repository.getPost()
                repository.getUsers().map { user ->
                    val postCount = posts.filter { it.userId == user.userId }.size
                    emit(user.copy(postCount = postCount))
                }
            }
        }
    }
}