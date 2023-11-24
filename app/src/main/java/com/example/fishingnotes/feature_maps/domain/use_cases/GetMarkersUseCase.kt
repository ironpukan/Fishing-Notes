package com.example.fishingnotes.feature_maps.domain.use_cases

import com.example.fishingnotes.feature_maps.domain.model.Marker
import com.example.fishingnotes.feature_maps.domain.repository.MarkerRepository
import kotlinx.coroutines.flow.Flow

class GetMarkersUseCase(
    private val repository: MarkerRepository
) {
    operator fun invoke() : Flow<List<Marker>> {
        return repository.getMarkers()
    }
}