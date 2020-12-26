package com.example.louyulin.kotlinweather.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.louyulin.kotlinweather.logic.model.Place
import com.example.louyulin.kotlinweather.logic.network.SunnyWeatherNetWork
import kotlinx.coroutines.Dispatchers
import java.lang.RuntimeException
//仓库层代码
object Repository {

    fun searchPlace(query: String): LiveData<Result<List<Place>>> {
        return liveData(Dispatchers.IO) {
            val result = try {
                val placeResponse = SunnyWeatherNetWork.searchPlace(query)
                if (placeResponse.status.equals("ok")) {
                    val places = placeResponse.places
                    Result.success(places)
                } else {
                    Result.failure(RuntimeException("response status is ${placeResponse.status}"))
                }
            } catch (e: Exception) {
                Result.failure<List<Place>>(e)
            }
            emit(result)
        }


    }
}