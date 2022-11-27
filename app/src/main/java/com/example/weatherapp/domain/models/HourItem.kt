package com.example.weatherapp.domain.models

data class HourItem (
    val time :String,
    val condition: String,
    val imageUrl:String,
    val temp:String
    )