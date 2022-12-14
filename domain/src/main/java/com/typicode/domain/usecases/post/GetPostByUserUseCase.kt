package com.typicode.domain.usecases.post

import com.typicode.domain.models.Post
import com.typicode.domain.models.User
import com.typicode.domain.repositories.Repository
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
class GetPostByUserUseCase constructor(
    private val repository: Repository
) : BaseUseCase<List<Post>, GetPostByUserUseCase.Params>() {

    override suspend fun remoteWork(params: Params?): List<Post> {
        return withContext(Dispatchers.IO) {
            repository.getPost().filter { it.userId == params!!.userId }
        }
    }

    data class Params(
        val userId: Int
    )
}