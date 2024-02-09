package com.example.androidgreeting.service

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object GreetingService {
    private val retrofit: Retrofit by lazy {
        val gson = GsonBuilder()
            .serializeNulls()
            .create()
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("http://121.41.43.46:9000/")
            .build()
    }

    val instance: GreetingApi by lazy {
        retrofit.create(GreetingApi::class.java)
    }
}

interface GreetingApi {
    @GET("/jonathan")
    fun greet(): Call<GreetingInfo>
}

data class GreetingInfo(
    val message: String
)

