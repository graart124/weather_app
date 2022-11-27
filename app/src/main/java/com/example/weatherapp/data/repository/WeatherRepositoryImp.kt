package com.example.weatherapp.data.repository


import com.example.weatherapp.data.api.WeatherApi
import com.example.weatherapp.data.models.Forecastday
import com.example.weatherapp.data.models.Hour
import com.example.weatherapp.data.models.Weather
import com.example.weatherapp.domain.models.DayItem
import com.example.weatherapp.domain.models.HourItem
import com.example.weatherapp.domain.models.WeatherItem
import com.example.weatherapp.domain.repository.WeatherRepository

class WeatherRepositoryImp :WeatherRepository {

    override suspend fun getCurrentWeather(city:String): WeatherItem {
        val weather = WeatherApi.retrofitService.getForecastWeather(city = city, days = 1)
        return weather.body()?.let { dataToDomain(it) }!!
    }

    override suspend fun getCurrentWeather(latitude: Double, longitude: Double): WeatherItem {
        val coordinate = "$latitude,$longitude"
        val weather = WeatherApi.retrofitService.getForecastWeatherByCoordinate(latitudeAndLongitude = coordinate, days = 1)
        return weather.body()?.let { dataToDomain(it) }!!
    }

    override suspend fun getDaysForecast(city: String, days: Int): List<DayItem> {
        val weather = WeatherApi.retrofitService.getForecastWeather(city = city, days = days)
        return forecastToDomain(weather.body()!!.forecast.forecastday)
    }

    override suspend fun getDaysForecast(
        latitude: Double,
        longitude: Double,
        days: Int
    ): List<DayItem> {
        val coordinate = "$latitude,$longitude"
        val weather = WeatherApi.retrofitService.getForecastWeatherByCoordinate(latitudeAndLongitude = coordinate, days = days)
        return forecastToDomain(weather.body()!!.forecast.forecastday)
    }

    override suspend fun getHoursForecast(city: String): List<HourItem> {
        val weather = WeatherApi.retrofitService.getForecastWeather(city = city, days = 1)
        val currentTime = weather.body()!!.location.localtime.substringAfter(' ').substringBefore(':').toInt()

        return dataToHours(weather.body()!!.forecast.forecastday[0].hour,currentTime)
    }

    override suspend fun getHoursForecast(latitude: Double, longitude: Double): List<HourItem> {
        val coordinate = "$latitude,$longitude"
        val weather = WeatherApi.retrofitService.getForecastWeatherByCoordinate(latitudeAndLongitude = coordinate, days = 1)
        val currentTime = weather.body()!!.location.localtime.substringAfter(' ').substringBefore(':').toInt()
        return dataToHours(weather.body()!!.forecast.forecastday[0].hour,currentTime)
    }

    private fun dataToHours(hour: List<Hour>,currentTime:Int):List<HourItem>{
        val hours = mutableListOf<HourItem>()
        hour.forEach{
            if(it.time.substringAfter(' ').substringBefore(':').toInt() >= currentTime) {
                hours.add(
                    HourItem(
                        it.time.substringAfter(' '),
                        it.condition.text,
                        it.condition.icon,
                        it.temp_c.toString()
                    )
                )
            }
        }
        return hours
    }
    private fun forecastToDomain(forecast: List<Forecastday>):List<DayItem>{
        val days = mutableListOf<DayItem>()
        forecast.forEach {
            days.add(
                DayItem(
                   it.date,
                   it.day.condition.text,
                   it.day.condition.icon,
                   it.day.mintemp_c,
                   it.day.maxtemp_c
                )
            )

        }
        return days
    }

    private fun dataToDomain(weather:Weather):WeatherItem{

        return WeatherItem(
            weather.location.name,
            weather.current.last_updated,
            weather.current.condition.text,
            weather.current.temp_c.toString(),
            weather.forecast.forecastday[0].day.maxtemp_c.toString(),
            weather.forecast.forecastday[0].day.mintemp_c.toString(),
            weather.current.condition.icon
        )
    }

}