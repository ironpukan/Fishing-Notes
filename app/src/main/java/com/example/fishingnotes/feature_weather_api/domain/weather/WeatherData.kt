package com.example.fishingnotes.feature_weather_api.domain.weather


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class WeatherData(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,
    val weatherType: WeatherType,
    val noteId: Int? = null,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)
