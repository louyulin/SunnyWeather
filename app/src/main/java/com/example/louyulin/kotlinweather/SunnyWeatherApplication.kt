package com.example.louyulin.kotlinweather

import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {
    companion object{
        const val TOKEN : String = "lxivnJ2M28qaVp4i"//token 令牌
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}