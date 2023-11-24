package com.example.fishingnotes.feature_weather_api.data.mappers

import androidx.room.TypeConverter
import com.example.fishingnotes.feature_notes.domain.model.NoteWithWeatherData
import com.example.fishingnotes.feature_weather_api.data.remote.WeatherDataDto
import com.example.fishingnotes.feature_weather_api.data.remote.WeatherDto
import com.example.fishingnotes.feature_weather_api.domain.weather.WeatherData
import com.example.fishingnotes.feature_weather_api.domain.weather.WeatherInfo
import com.example.fishingnotes.feature_weather_api.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {

    @TypeConverter
    fun fromTimestamp(value: String): LocalDateTime {
        return LocalDateTime.parse(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime): String {
        return date.toString()
    }

    @TypeConverter
    fun fromWeatherType(weatherType: WeatherType) : Int {
        return weatherType.iconRes
    }

    @TypeConverter
    fun toWeatherType(iconRes: Int) : WeatherType {
        return WeatherType.fromWMO(iconRes)
    }
}

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun NoteWithWeatherData.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return weatherData.mapIndexed { index, weatherData ->
        IndexedWeatherData(index, weatherData)
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}

fun NoteWithWeatherData.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = when {
            now.minute < 30 -> now.hour
            now.hour == 23 -> 0
            else -> now.hour + 1
        }
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}


fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressure[index]
        val humidity = humidities[index]

        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                windSpeed = windSpeed,
                pressure = pressure,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}


fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = when {
            now.minute < 30 -> now.hour
            now.hour == 23 -> 0
            else -> now.hour + 1
        }
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}