package com.levivas.reddit.application.injection

import com.levivas.reddit.application.network.calls.PostsCalls
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory
import com.levivas.reddit.BuildConfig
import com.levivas.reddit.utils.MoshiDateAdapter
import com.levivas.reddit.utils.RETROFIT_TIMEOUT
import com.squareup.moshi.Moshi

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                addInterceptor(loggingInterceptor)
            }
            connectTimeout(timeout = RETROFIT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(timeout = RETROFIT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(timeout = RETROFIT_TIMEOUT, TimeUnit.SECONDS)
        }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: Lazy<OkHttpClient>): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .callFactory { request -> okHttpClient.get().newCall(request) }
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().add(MoshiDateAdapter()).build()
            )
        )
        .build()

    @Singleton
    @Provides
    fun providePostsCalls(retrofit: Retrofit): PostsCalls = retrofit.create(PostsCalls::class.java)
}