package com.example.louyulin.kotlinweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.louyulin.kotlinweather.logic.Repository
import com.example.louyulin.kotlinweather.logic.model.Place

class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData){
        query ->
        Repository.searchPlace(query)
    }

    //将传入的参数赋值给searchLiveData 对象
    fun searchPlace(query:String){
        searchLiveData.value = query
    }

    fun savePlace(place:Place){
        Repository.savePlace(place)
    }

    fun getSavePlace(): Place {
        val savePlace = Repository.getSavePlace()
        return savePlace
    }

    fun isPlaceSaved(): Boolean {
        return Repository.isPlaceSaved()
    }
}