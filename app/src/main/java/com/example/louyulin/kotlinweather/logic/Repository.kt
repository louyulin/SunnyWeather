package com.example.louyulin.kotlinweather.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.louyulin.kotlinweather.logic.dao.PlaceDao
import com.example.louyulin.kotlinweather.logic.model.Place
import com.example.louyulin.kotlinweather.logic.network.SunnyWeatherNetWork
import com.sunnyweather.android.logic.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
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

    fun refreshWeather(lng: String, lat: String): LiveData<Result<Weather>> {
        return liveData(Dispatchers.IO) {
            val result = try {
                coroutineScope {
                    val deferredRealtime = async {
                        SunnyWeatherNetWork.getRealtimeWeather(lng, lat)
                    }
                    val deferreDaily = async {
                        SunnyWeatherNetWork.getDailyWeather(lng, lat)
                    }
                    val realtimeResponse = deferredRealtime.await()
                    val dailyResponse = deferreDaily.await()
                    if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                        val weather =
                            Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                        Result.success(weather)
                    } else {
                        Result.failure(RuntimeException("realtime response and daily response failure"))
                    }
                }
            } catch (e: Exception) {
                Result.failure<Weather>(e)
            }
            emit(result)
        }
    }

    fun savePlace(place: Place) {
        PlaceDao.savePlace(place)
    }

    fun getSavePlace(): Place {
        val sacePlace = PlaceDao.getSacePlace()
        return sacePlace
    }

    fun isPlaceSaved(): Boolean {
       return PlaceDao.isPlaceSaved()
    }
}