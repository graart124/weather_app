package com.example.weatherapp.di

import android.content.Context
import com.example.weatherapp.data.repository.WeatherRepositoryImp
import com.example.weatherapp.data.storage.SharedPrefLocationStorage
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideWeatherRepository():WeatherRepository{
        return WeatherRepositoryImp()
    }

    @Provides
    @Singleton
    fun provideLocationStorage(@ApplicationContext context: Context):SharedPrefLocationStorage{
        return SharedPrefLocationStorage(context)
    }

}