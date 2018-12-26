package com.design.copluk.copluksample.di

import com.design.copluk.copluksample.network.ApiRoute
import dagger.Module
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import javax.inject.Singleton
import dagger.Provides

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideGithubService(): ApiRoute {
        return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create<ApiRoute>(ApiRoute::class.java)
    }

}