package com.example.louyulin.kotlinweather.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.louyulin.kotlinweather.R
import com.example.louyulin.kotlinweather.logic.model.Place
import com.example.louyulin.kotlinweather.ui.weather.WeatherActivity
import com.sunnyweather.android.logic.model.Weather
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceAdapter(
    private val fragment: PlaceFragment,
    private val placeList: List<Place>
) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.place_item, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener{
            val position = viewHolder.adapterPosition
            val place = placeList[position]
            val activity = fragment.activity
            if (activity is WeatherActivity){
                //侧滑菜单的点击城市列表
                activity.drawerLayout.closeDrawers()
                activity.viewModel.locationLng = place.location.lng
                activity.viewModel.locationLat = place.location.lat
                activity.viewModel.placeName = place.name
                activity.refreshWeather()
            }else{
                //第一次进入app点击城市列表
                val intent = Intent(parent.context, WeatherActivity::class.java).apply {
                    putExtra("location_lng" , place.location.lng)
                    putExtra("location_lat" , place.location.lat)
                    putExtra("place_name" , place.name)
                }
                fragment.startActivity(intent)
                fragment.activity?.finish()
            }
            //点击之后保存当前点击的城市
            fragment.viewModel.savePlace(place)
            fragment.searchPlaceEdit.setText("")
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        if (placeList == null){
            return  0 ;
        }else{
            return placeList.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.setText(place.name)
        holder.placeAddress.setText(place.address)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }
}