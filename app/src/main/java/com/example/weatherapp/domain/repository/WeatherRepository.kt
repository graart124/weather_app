package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.models.DayItem
import com.example.weatherapp.domain.models.HourItem
import com.example.weatherapp.domain.models.WeatherItem

interface WeatherRepository {
    suspend fun getCurrentWeather(city:String):WeatherItem
    suspend fun getDaysForecast(city: String,days:Int=5):List<DayItem>
    suspend fun getHoursForecast(city: String):List<HourItem>

    suspend fun getCurrentWeather(latitude:Double,longitude:Double):WeatherItem
    suspend fun getDaysForecast(latitude:Double,longitude:Double,days:Int=5):List<DayItem>
    suspend fun getHoursForecast(latitude:Double,longitude:Double):List<HourItem>

}