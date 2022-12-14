package com.typicode.android.common.di

import com.typicode.android.features.MainVM
import com.typicode.android.features.posts.PostVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelsModule = module {
    viewModel<MainVM>()
    viewModel<PostVM>()
}

val appModule = arrayOf(
    viewModelsModule
)
