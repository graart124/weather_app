package com.example.weatherapp.domain.models

data class WeatherItem(
    val city:String,
    val time:String,
    val condition:String,
    val currentTemp:String,
    val maxTemp:String,
    val minTemp:String,
    val imageUrl:String
)
