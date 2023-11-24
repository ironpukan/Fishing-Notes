package com.example.fishingnotes.feature_weather_api.domain.repository

import com.example.fishingnotes.feature_weather_api.domain.weather.WeatherData
import com.example.fishingnotes.feature_weather_api.domain.weather.WeatherInfo
import com.plcoding.weatherapp.domain.util.Resource
import java.time.LocalDateTime

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}

interface WeatherRepositoryLocal {

    suspend fun addWeatherData(weatherData: WeatherData)

    suspend fun deleteWeatherDataWithNoteId(noteId: Int)

    suspend fun getWeatherDataByHour(hour: LocalDateTime, noteId: Int) : WeatherData

}