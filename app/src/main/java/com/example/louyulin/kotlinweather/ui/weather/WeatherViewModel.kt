package com.example.louyulin.kotlinweather.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.louyulin.kotlinweather.logic.Repository
import com.example.louyulin.kotlinweather.logic.model.Location
import com.sunnyweather.android.logic.model.Weather

class WeatherViewModel : ViewModel() {
    private val locationLiveData = MutableLiveData<Location>()

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    val weatherLiveData: LiveData<Result<Weather>> =
        Transformations.switchMap(locationLiveData) { it ->
            Repository.refreshWeather(it.lng, it.lat)
        }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng, lat)
    }


}