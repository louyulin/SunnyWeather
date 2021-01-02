package com.example.louyulin.kotlinweather.logic.network

import com.example.louyulin.kotlinweather.logic.model.PlaceResponse
import com.sunnyweather.android.logic.model.DailyResponse
import com.sunnyweather.android.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

//网络请求入口
object SunnyWeatherNetWork {
    private val placeService = ServiceCreator.create(PleaceService::class.java)

    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    suspend fun searchPlace(query: String): PlaceResponse {
        return placeService.searchPlace(query).await()
    }

    suspend fun getDailyWeather(lng:String , lat:String): DailyResponse {
        return weatherService.getDailyWeather(lng , lat).await()
    }

    suspend fun getRealtimeWeather(lng: String , lat: String): RealtimeResponse {
        return weatherService.getRealtimeWeather(lng , lat).await()
    }




    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        continuation.resume(body)
                    } else {
                        continuation.resumeWithException(RuntimeException("response is null"))
                    }
                }

            })
        }
    }

}