package com.example.louyulin.kotlinweather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//retrofit 构造器
object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com/"
    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(servicesClass: Class<T>): T {
        return retrofit.create(servicesClass)
    }

}