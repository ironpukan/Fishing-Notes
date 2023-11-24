package com.example.fishingnotes.feature_maps.domain.use_cases

import com.example.fishingnotes.feature_maps.domain.model.Marker
import com.example.fishingnotes.feature_maps.domain.repository.MarkerRepository

class DeleteMarkerUseCase(
    private val repository: MarkerRepository
) {
    suspend operator fun invoke(marker: Marker) {
        repository.deleteMarker(marker)
    }
}