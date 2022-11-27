package com.example.weatherapp.data.storage

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext


private const val SHARED_PREF_NAME="shared_pref_name"
private const val LOCATION_KEY = "location_key"
const val LOCATION_NOT_SAVED = "location not saved"


class SharedPrefLocationStorage(@ApplicationContext context: Context) {
    private val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE)

    fun set(location :String){
        sharedPreferences.edit().putString(LOCATION_KEY,location).apply()
    }

    fun get():String{
        return sharedPreferences.getString(LOCATION_KEY, LOCATION_NOT_SAVED)?: LOCATION_NOT_SAVED
    }

}