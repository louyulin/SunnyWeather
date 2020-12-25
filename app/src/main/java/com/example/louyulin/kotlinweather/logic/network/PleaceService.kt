package com.example.louyulin.kotlinweather.logic.network

import com.example.louyulin.kotlinweather.SunnyWeatherApplication
import com.example.louyulin.kotlinweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PleaceService {
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlace(@Query("query") quert: String): Call<PlaceResponse>

}