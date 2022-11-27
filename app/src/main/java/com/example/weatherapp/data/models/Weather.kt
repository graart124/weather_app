package com.example.weatherapp.data.models

data class Weather(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)