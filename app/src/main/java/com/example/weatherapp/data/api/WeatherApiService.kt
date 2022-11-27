package com.example.weatherapp.data.api

import com.example.weatherapp.data.models.Weather
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiService {

    @GET("forecast.json")
    suspend fun getForecastWeather(@Query("key") api:String= API
                           ,@Query("q") city:String,
                            @Query("days") days:Int
    ):Response<Weather>

    @GET("forecast.json")
    suspend fun getForecastWeatherByCoordinate(@Query("key") api:String= API
                                   ,@Query("q") latitudeAndLongitude:String,
                                   @Query("days") days:Int
    ):Response<Weather>


    @GET("current.json")
    suspend fun getCurrentWeather(@Query("key") api:String= API
                                  ,@Query("q") city:String
    ):Response<Weather>

}

//http://api.weatherapi.com/v1/forecast.json?key=d6b53724c7604ecbbd5182437222706&q=London&days=3

private const val API = "d6b53724c7604ecbbd5182437222706"
object WeatherApi{
    private val retrofit=Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://api.weatherapi.com/v1/")
        .build()

    val retrofitService:WeatherApiService by lazy{
        retrofit.create(WeatherApiService::class.java)
    }
}