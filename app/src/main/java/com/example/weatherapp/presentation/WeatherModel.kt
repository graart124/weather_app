package com.example.weatherapp.presentation


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.storage.LOCATION_NOT_SAVED
import com.example.weatherapp.data.storage.SharedPrefLocationStorage
import com.example.weatherapp.domain.models.DayItem
import com.example.weatherapp.domain.models.HourItem
import com.example.weatherapp.domain.models.WeatherItem
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor( private val repository : WeatherRepository,
                                            private val storage:SharedPrefLocationStorage):ViewModel() {

    private val _currentWeather = MutableLiveData<WeatherItem>()
    val currentWeather:LiveData<WeatherItem> = _currentWeather

    private val _listOfDays = MutableLiveData<List<DayItem>>()
    val listOfDays:LiveData<List<DayItem>> = _listOfDays

    private val _listOfHours = MutableLiveData<List<HourItem>>()
    val listOfHours:LiveData<List<HourItem>> = _listOfHours


    fun setCityAndUpdateData(location: String){
        viewModelScope.launch {
            try {
                _currentWeather.value = repository.getCurrentWeather(location)
                _listOfHours.value = repository.getHoursForecast(location)
                _listOfDays.value = repository.getDaysForecast(location)
                storage.set(location)
            }
            catch(e:Exception){
               Log.d("MyLog","$location not correct")
            }
        }
    }


    fun setCoordinatesAndUpdateData(latitude:Double,longitude:Double){
        viewModelScope.launch {
            try {
                _currentWeather.value=repository.getCurrentWeather(latitude,longitude)
                _listOfDays.value=repository.getDaysForecast(latitude,longitude)
                _listOfHours.value=repository.getHoursForecast(latitude,longitude)
                storage.set(_currentWeather.value!!.city)
            }
            catch (e:Exception){
                Log.d("MyLog","cant find location by coordinates")
            }
        }
    }

    fun loadSavedLocationWeatherData():Boolean{
        val location = storage.get()
        if (location == LOCATION_NOT_SAVED)
            return false
        viewModelScope.launch {
            try {
                _currentWeather.value = repository.getCurrentWeather(location)
                _listOfHours.value = repository.getHoursForecast(location)
                _listOfDays.value = repository.getDaysForecast(location)
            }
            catch(e:Exception){
                Log.d("MyLog","Saved location:$location not correct")
            }
        }

        return true
    }
}