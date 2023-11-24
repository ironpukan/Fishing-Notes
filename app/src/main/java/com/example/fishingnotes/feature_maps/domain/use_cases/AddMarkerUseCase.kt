package com.example.fishingnotes.feature_maps.domain.use_cases

import com.example.fishingnotes.feature_maps.domain.model.InvalidMarkerException
import com.example.fishingnotes.feature_maps.domain.model.Marker
import com.example.fishingnotes.feature_maps.domain.repository.MarkerRepository

class AddMarkerUseCase(
    private val repository: MarkerRepository
) {
    @Throws(InvalidMarkerException::class)
    suspend operator fun invoke(marker: Marker) {
        if (marker.markerTitle.isBlank()) {
            throw InvalidMarkerException("Marker title can't be empty!")
        }
        repository.addMarker(marker)
    }
}