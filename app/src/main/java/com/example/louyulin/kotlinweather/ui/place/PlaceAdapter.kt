package com.example.louyulin.kotlinweather.ui.place

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.louyulin.kotlinweather.R
import com.example.louyulin.kotlinweather.logic.model.Place

class PlaceAdapter(
    private val fragment: Fragment,
    private val palceList: List<Place>
) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.place_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (palceList == null){
            return  0 ;
        }else{
            return palceList.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = palceList[position]
        holder.placeName.setText(place.name)
        holder.placeAddress.setText(place.address)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }
}