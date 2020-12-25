package com.example.louyulin.kotlinweather.logic.network

import com.example.louyulin.kotlinweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

//网络请求入口
object SunnyWeatherNetWork {
    private val placeService = ServiceCreator.create(PleaceService::class.java)

    suspend fun searchPlace(query: String): PlaceResponse {
        return placeService.searchPlace(query).await()
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