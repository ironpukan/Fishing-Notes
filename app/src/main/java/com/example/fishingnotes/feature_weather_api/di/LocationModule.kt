package com.example.fishingnotes.feature_weather_api.di

import com.example.fishingnotes.feature_weather_api.data.location.DefaultLocationTracker
import com.example.fishingnotes.feature_weather_api.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(defaultLocationTracker: DefaultLocationTracker): LocationTracker
}