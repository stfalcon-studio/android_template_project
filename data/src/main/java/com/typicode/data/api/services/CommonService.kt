package com.typicode.data.api.services

import com.typicode.data.api.models.responses.PostResponse
import com.typicode.data.api.models.responses.UserResponse
import retrofit2.http.GET

interface CommonService {

    @GET("test_resources/posts")
    suspend fun getPosts(): List<PostResponse>

    @GET("test_resources/users")
    suspend fun getUsers(): List<UserResponse>
}
