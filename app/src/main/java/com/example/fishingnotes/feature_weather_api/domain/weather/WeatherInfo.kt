package com.example.fishingnotes.feature_weather_api.domain.weather


data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?
)