package com.example.fishingnotes.feature_weather_api.domain.use_cases

data class WeatherUseCases(
    val addWeatherDataUseCase: AddWeatherDataUseCase,
    val deleteWeatherDataWithNoteId: DeleteWeatherDataWithNoteId,
    val getWeatherDataByHour: GetWeatherDataByHour
)