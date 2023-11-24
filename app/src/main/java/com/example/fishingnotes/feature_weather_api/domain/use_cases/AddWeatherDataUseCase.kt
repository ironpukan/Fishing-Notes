package com.example.fishingnotes.feature_weather_api.domain.use_cases

import com.example.fishingnotes.feature_weather_api.domain.repository.WeatherRepositoryLocal
import com.example.fishingnotes.feature_weather_api.domain.weather.WeatherData

class AddWeatherDataUseCase(
    private val repositoryLocal: WeatherRepositoryLocal
) {
    suspend operator fun invoke(weatherData: WeatherData) {
        repositoryLocal.addWeatherData(weatherData)
    }
}