package com.example.fishingnotes.feature_maps.domain.use_cases

import com.example.fishingnotes.feature_maps.domain.model.MarkerWithNotes
import com.example.fishingnotes.feature_maps.domain.repository.MarkerRepository

class GetMarkerWithNotesUseCase(
    private val repository: MarkerRepository
) {
    suspend operator fun invoke(id: Int): MarkerWithNotes {
        return repository.getMarkerWithNotes(id)
    }
}