package com.example.fishingnotes.feature_weather_api.di

import com.example.fishingnotes.feature_weather_api.data.location.DefaultLocationTracker
import com.example.fishingnotes.feature_weather_api.data.repository.WeatherRepositoryImpl
import com.example.fishingnotes.feature_weather_api.data.repository.WeatherRepositoryImpl_Factory
import com.example.fishingnotes.feature_weather_api.domain.location.LocationTracker
import com.example.fishingnotes.feature_weather_api.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}