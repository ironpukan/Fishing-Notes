package com.example.fishingnotes.feature_weather_api.presentation

import com.example.fishingnotes.feature_weather_api.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)