package com.example.fishingnotes.feature_weather_api.domain.use_cases

import com.example.fishingnotes.feature_weather_api.domain.repository.WeatherRepositoryLocal
import com.example.fishingnotes.feature_weather_api.domain.weather.WeatherData
import java.time.LocalDateTime

class GetWeatherDataByHour(
    private val repositoryLocal: WeatherRepositoryLocal
) {
    suspend operator fun invoke(timestamp: LocalDateTime, noteId: Int) : WeatherData {
        return repositoryLocal.getWeatherDataByHour(timestamp, noteId)
    }
}