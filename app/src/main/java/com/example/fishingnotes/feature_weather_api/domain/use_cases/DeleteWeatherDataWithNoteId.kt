package com.example.fishingnotes.feature_weather_api.domain.use_cases

import com.example.fishingnotes.feature_weather_api.domain.repository.WeatherRepositoryLocal

class DeleteWeatherDataWithNoteId(
    private val repositoryLocal: WeatherRepositoryLocal
) {
    suspend operator fun invoke(noteId: Int) {
        repositoryLocal.deleteWeatherDataWithNoteId(noteId)
    }
}