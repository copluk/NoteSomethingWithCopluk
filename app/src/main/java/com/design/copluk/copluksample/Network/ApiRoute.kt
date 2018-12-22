package com.design.copluk.copluksample.Network

import com.design.copluk.copluksample.model.googleMap.DirectionResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface ApiRoute{
    @GET()
    fun getDirection(@Url() url: String): Call<DirectionResults>
}