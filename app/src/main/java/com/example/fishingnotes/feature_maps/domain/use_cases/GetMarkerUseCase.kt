package com.example.fishingnotes.feature_maps.domain.use_cases

import com.example.fishingnotes.feature_maps.domain.model.Marker
import com.example.fishingnotes.feature_maps.domain.repository.MarkerRepository

class GetMarkerUseCase(
    private val repository: MarkerRepository
) {
    suspend operator fun invoke(id: Int): Marker? {
        return repository.getMarkerById(id)
    }
}