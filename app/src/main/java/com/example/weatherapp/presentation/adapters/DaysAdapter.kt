package com.example.weatherapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ListItemBinding
import com.example.weatherapp.domain.models.DayItem

class DaysAdapter:ListAdapter<DayItem,DaysAdapter.DaysViewHolder>(DiffCallBack) {

    class DaysViewHolder(view:View):RecyclerView.ViewHolder(view){
        private val binding = ListItemBinding.bind(view)
        fun bind(weather: DayItem)= with(binding){
            tvDate.text=weather.date
            textCondition.text=weather.condition
            ivCondition.load("https:"+weather.imageUrl)

            val maxMinTemp=String.format("%.1f", ((weather.maxTemp+weather.minTemp)/2))+"°C"
            tvTemp.text=maxMinTemp
        }
    }

    companion object{
        private val DiffCallBack = object :DiffUtil.ItemCallback<DayItem>(){
            override fun areItemsTheSame(oldItem: DayItem, newItem: DayItem): Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(oldItem: DayItem, newItem: DayItem): Boolean {
                return oldItem==newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return DaysViewHolder(view)
    }

    override fun onBindViewHolder(holder: DaysViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}