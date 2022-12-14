package com.typicode.domain.di

import com.typicode.domain.usecases.post.GetPostByUserUseCase
import com.typicode.domain.usecases.users.SubscribeToUsersUseCase
import org.koin.dsl.factory
import org.koin.dsl.module

private val useCasesModule = module {
    factory<SubscribeToUsersUseCase>()
    factory<GetPostByUserUseCase>()
}

val domainModule = arrayOf(
    useCasesModule
)
