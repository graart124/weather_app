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
import com.example.weatherapp.domain.models.HourItem


class HourAdapter : ListAdapter<HourItem, HourAdapter.Holder>(Comparator()) {

    class Holder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = ListItemBinding.bind(view)

        fun bind(item: HourItem) = with(binding){
            tvDate.text = item.time
            textCondition.text = item.condition
            tvTemp.text = item.temp+"°C"
            ivCondition.load("https:"+item.imageUrl)
        }
    }

    class Comparator : DiffUtil.ItemCallback<HourItem>(){
        override fun areItemsTheSame(oldItem: HourItem, newItem: HourItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HourItem, newItem: HourItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}