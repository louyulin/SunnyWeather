package com.example.louyulin.kotlinweather.logic.dao

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.louyulin.kotlinweather.SunnyWeatherApplication
import com.example.louyulin.kotlinweather.logic.model.Place
import com.google.gson.Gson

object PlaceDao {
    private fun sharedPerferences(): SharedPreferences? {
        return SunnyWeatherApplication.context.getSharedPreferences(
            "sunny_weather",
            Context.MODE_PRIVATE
        )
    }

    fun isPlaceSaved(): Boolean {
        return sharedPerferences()!!.contains("place")
    }

    fun savePlace(place: Place) {
        sharedPerferences()!!.edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSacePlace(): Place {
        val placeJson = sharedPerferences()!!.getString("place", "")
        val place = Gson().fromJson(placeJson, Place::class.java)
        return place
    }
}