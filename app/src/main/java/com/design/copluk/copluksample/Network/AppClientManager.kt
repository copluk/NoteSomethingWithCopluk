package com.design.copluk.copluksample.Network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppClientManager{
    private var retrofit : Retrofit
    var okHttpClient = OkHttpClient()
    private val baseUrl = "https://google.com"

    init{
        okHttpClient = OkHttpClient()
        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }

    fun getClient(): Retrofit {
        return this.retrofit
    }



}