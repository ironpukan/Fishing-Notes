package com.example.fishingnotes.feature_weather_api.di

import android.app.Application
import com.example.fishingnotes.feature_weather_api.data.remote.WeatherApi
import com.example.fishingnotes.feature_weather_api.data.repository.WeatherRepositoryLocalImpl
import com.example.fishingnotes.feature_weather_api.domain.repository.WeatherRepositoryLocal
import com.example.fishingnotes.feature_weather_api.domain.use_cases.AddWeatherDataUseCase
import com.example.fishingnotes.feature_weather_api.domain.use_cases.DeleteWeatherDataWithNoteId
import com.example.fishingnotes.feature_weather_api.domain.use_cases.GetWeatherDataByHour
import com.example.fishingnotes.feature_weather_api.domain.use_cases.WeatherUseCases
import com.example.fishingnotes.local_data_sourse.FishingNotesDatabase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(db: FishingNotesDatabase): WeatherRepositoryLocal {
        return WeatherRepositoryLocalImpl(dao = db.weatherDao)
    }

    @Provides
    @Singleton
    fun provideWeatherUseCases(repository: WeatherRepositoryLocal): WeatherUseCases {
        return WeatherUseCases(
            AddWeatherDataUseCase(repository),
            DeleteWeatherDataWithNoteId(repository),
            GetWeatherDataByHour(repository)
        )
    }
}