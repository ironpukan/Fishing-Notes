package com.example.fishingnotes.feature_weather_api.data.repository

import com.example.fishingnotes.feature_weather_api.data.data_sorse.WeatherDao.WeatherDao
import com.example.fishingnotes.feature_weather_api.data.mappers.toWeatherInfo
import com.example.fishingnotes.feature_weather_api.data.remote.WeatherApi
import com.example.fishingnotes.feature_weather_api.domain.repository.WeatherRepository
import com.example.fishingnotes.feature_weather_api.domain.repository.WeatherRepositoryLocal
import com.example.fishingnotes.feature_weather_api.domain.weather.WeatherData
import com.example.fishingnotes.feature_weather_api.domain.weather.WeatherInfo
import com.plcoding.weatherapp.domain.util.Resource
import java.time.LocalDateTime
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occured")
        }
    }
}

class WeatherRepositoryLocalImpl @Inject constructor(
    private val dao: WeatherDao
) : WeatherRepositoryLocal {

    override suspend fun addWeatherData(weatherData: WeatherData) {
        dao.addWeatherData(weatherData)
    }

    override suspend fun deleteWeatherDataWithNoteId(noteId: Int) {
        dao.deleteWeatherDataWithNoteId(noteId)
    }

    override suspend fun getWeatherDataByHour(hour: LocalDateTime, noteId: Int): WeatherData {
        return dao.getWeatherDataByHour(hour, noteId)
    }

}