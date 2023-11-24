package com.example.fishingnotes.feature_notes.domain.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.fishingnotes.feature_weather_api.domain.weather.WeatherData

data class NoteWithWeatherData(
    @Embedded val note: Note,
    @Relation(
        parentColumn = "id",
        entityColumn = "noteId"
    )
    val weatherData: List<WeatherData>
)