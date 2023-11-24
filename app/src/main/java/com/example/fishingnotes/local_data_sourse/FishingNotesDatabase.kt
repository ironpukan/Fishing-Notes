package com.example.fishingnotes.local_data_sourse

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fishingnotes.feature_maps.data.data_sourse.MarkerDao.MarkerDao
import com.example.fishingnotes.feature_maps.domain.model.Marker
import com.example.fishingnotes.feature_notes.data.data_sourse.NoteDao.NoteDao
import com.example.fishingnotes.feature_notes.domain.model.Note
import com.example.fishingnotes.feature_weather_api.data.data_sorse.WeatherDao.WeatherDao
import com.example.fishingnotes.feature_weather_api.data.mappers.Converters
import com.example.fishingnotes.feature_weather_api.domain.weather.WeatherData

@Database(
    entities = [
        Note::class,
        Marker::class,
        WeatherData::class
    ],
    version = 5,
)
@TypeConverters(Converters::class)
abstract class FishingNotesDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
    abstract val markerDao: MarkerDao
    abstract val weatherDao: WeatherDao

    companion object {
        const val DATABASE_NAME = "fishing_notes_db"
    }
}