package com.example.fishingnotes.feature_maps.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Marker(
    val markerTitle: String,
    val latitude: Double,
    val longitude: Double,
    @PrimaryKey val id: Int? = null
)
class InvalidMarkerException(message: String) : Exception(message)