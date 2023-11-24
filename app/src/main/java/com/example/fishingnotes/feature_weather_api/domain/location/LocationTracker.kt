package com.example.fishingnotes.feature_weather_api.domain.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}