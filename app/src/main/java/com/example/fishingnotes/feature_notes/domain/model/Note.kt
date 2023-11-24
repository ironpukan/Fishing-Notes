package com.example.fishingnotes.feature_notes.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.fishingnotes.feature_weather_api.data.remote.WeatherDataDto
import com.example.fishingnotes.feature_weather_api.domain.weather.WeatherData
import java.time.LocalDateTime

// TODO: Correct timestamp value
@Entity
data class Note(
    val title: String,
    val timestamp: LocalDateTime,
    val content: String,
    val markerId: Int? = null,
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
)

class InvalidNoteException(message: String) : Exception(message)