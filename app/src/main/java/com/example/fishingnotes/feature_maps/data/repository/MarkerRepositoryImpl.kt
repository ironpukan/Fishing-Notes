package com.example.fishingnotes.feature_maps.data.repository

import com.example.fishingnotes.feature_maps.data.data_sourse.MarkerDao.MarkerDao
import com.example.fishingnotes.feature_maps.domain.model.Marker
import com.example.fishingnotes.feature_maps.domain.model.MarkerWithNotes
import com.example.fishingnotes.feature_maps.domain.repository.MarkerRepository
import kotlinx.coroutines.flow.Flow

class MarkerRepositoryImpl(
    private val dao: MarkerDao
) : MarkerRepository {

    override fun getMarkers(): Flow<List<Marker>> {
        return dao.getMarkers()
    }

    override suspend fun getMarkerById(id: Int): Marker? {
        return dao.getMarkerById(id)
    }

    override suspend fun addMarker(marker: Marker) {
        dao.addMarker(marker)
    }

    override suspend fun deleteMarker(marker: Marker) {
        dao.deleteMarker(marker)
    }

    override suspend fun getMarkerWithNotes(id: Int) : MarkerWithNotes {
        return dao.getMarkerWithNotes(id)
    }
}