package com.example.fishingnotes.feature_maps.domain.repository

import com.example.fishingnotes.feature_maps.domain.model.Marker
import com.example.fishingnotes.feature_maps.domain.model.MarkerWithNotes
import kotlinx.coroutines.flow.Flow

interface MarkerRepository {
    fun getMarkers(): Flow<List<Marker>>

    suspend fun getMarkerById(id: Int): Marker?

    suspend fun addMarker(marker: Marker)

    suspend fun deleteMarker(marker: Marker)

    suspend fun getMarkerWithNotes(id: Int): MarkerWithNotes
}