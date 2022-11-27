package com.example.weatherapp.domain.models


data class DayItem(
    val date :String,
    val condition: String,
    val imageUrl:String,
    val minTemp:Double,
    val maxTemp:Double,
)
