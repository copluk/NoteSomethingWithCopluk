package com.design.copluk.copluksample.di

import com.design.copluk.copluksample.BuildConfig
import com.design.copluk.copluksample.network.ApiRoute
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import javax.inject.Singleton
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor { chain ->
            chain.proceed(
                    chain.request()
                            .newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .build()
            )
        }.connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val loggingHeader = HttpLoggingInterceptor()
            loggingHeader.level = HttpLoggingInterceptor.Level.HEADERS

            val loggingBody = HttpLoggingInterceptor()
            loggingBody.level = HttpLoggingInterceptor.Level.BODY

            okHttpBuilder.addInterceptor(loggingHeader)
            okHttpBuilder.addInterceptor(loggingBody)
        }

        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()

        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideGithubService(gson: Gson, okHttpClient: OkHttpClient): ApiRoute {
        return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
                .create<ApiRoute>(ApiRoute::class.java)
    }

}