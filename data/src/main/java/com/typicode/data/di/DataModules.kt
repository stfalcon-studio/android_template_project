package com.typicode.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.typicode.data.BuildConfig
import com.typicode.data.api.RestConst
import com.typicode.data.api.services.CommonService
import com.typicode.data.repositories.RepositoryImpl
import com.typicode.domain.repositories.Repository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.dsl.single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

private val repositoriesModule = module {
    single<RepositoryImpl>() bind Repository::class
}

private val apiServicesModule = module {
    single<CommonService> {
        (get() as Retrofit).create(CommonService::class.java)
    }
}

private val networkModule = module {
    factory<Gson> {
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ")
            .create()
    }

    factory<GsonConverterFactory> { GsonConverterFactory.create(get()) }

    factory {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        loggingInterceptor
    }

    factory {
        Interceptor {
            val builder = it.request().newBuilder()
                .url(it.request().url)
            builder.header(RestConst.HEADER_PLATFORM, RestConst.CONTENT_PLATFORM)
            builder.header(RestConst.HEADER_ACCEPT_LANGUAGE, Locale.getDefault().language)
            builder.header(RestConst.CONTENT_TYPE, RestConst.CONTENT_TYPE_JSON)

            it.proceed(builder.build())
        }
    }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    factory {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(get<GsonConverterFactory>())
            .client(get())
            .build()
    }
}

val dataModule = arrayOf(
    repositoriesModule,
    apiServicesModule,
    networkModule
)
