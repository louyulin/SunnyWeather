package com.example.louyulin.kotlinweather.ui.place

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.louyulin.kotlinweather.MainActivity
import com.example.louyulin.kotlinweather.R
import com.example.louyulin.kotlinweather.logic.Repository
import com.example.louyulin.kotlinweather.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.fragment_place.*
import kotlinx.android.synthetic.main.place_item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlaceFragment : Fragment() {
     val viewModel by lazy {
        ViewModelProviders.of(this)
            .get(PlaceViewModel::class.java)
    }

    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place  , container , false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is MainActivity && viewModel.isPlaceSaved()){
            val place = viewModel.getSavePlace()
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            startActivity(intent)
            activity?.finish()
        }
        val linearLayoutManager = LinearLayoutManager(this.activity)
        recyclerview.layoutManager = linearLayoutManager
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerview.adapter = adapter

        searchPlaceEdit.addTextChangedListener{
            it ->
            val content = it.toString()
            if (content.isNotEmpty()){
                viewModel.searchPlace(content)
            }else{
                recyclerview.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        viewModel.placeLiveData.observe(this , Observer {
            result ->
            val places = result.getOrNull()
            if (places != null){
                recyclerview.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(activity , "未能查询到任何地点" , Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }

        })
    }
}