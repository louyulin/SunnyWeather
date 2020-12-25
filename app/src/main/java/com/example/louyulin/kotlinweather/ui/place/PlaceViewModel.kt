package com.example.louyulin.kotlinweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.louyulin.kotlinweather.logic.Repository
import com.example.louyulin.kotlinweather.logic.model.Place

class PlaceViewModel : ViewModel() {
    private val searchLiceData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiceData = Transformations.switchMap(searchLiceData){
        query ->
        Repository.searchPlace(query)
    }

    //将传入的参数赋值给searchLiveData 对象
    fun searchPlace(query:String){
        searchLiceData.value = query
    }
}