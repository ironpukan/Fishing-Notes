package com.example.fishingnotes.feature_weather_api.data.data_sorse.WeatherDao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fishingnotes.feature_weather_api.domain.weather.WeatherData
import java.time.LocalDateTime

@Dao
interface WeatherDao {

    @Query("DELETE FROM WeatherData WHERE noteId = :noteId")
    suspend fun deleteWeatherDataWithNoteId(noteId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWeatherData(weatherData: WeatherData)

    @Query("SELECT * FROM WeatherData WHERE time = :hour AND noteId = :noteId")
    suspend fun getWeatherDataByHour(hour: LocalDateTime, noteId: Int) : WeatherData
}