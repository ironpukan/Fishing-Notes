package com.example.fishingnotes.feature_maps.domain.use_cases

data class MarkerUseCases(
    val getMarkerUseCase: GetMarkerUseCase,
    val addMarkerUseCase: AddMarkerUseCase,
    val getMarkersUseCase: GetMarkersUseCase,
    val deleteMarkerUseCase: DeleteMarkerUseCase,
    val getMarkerWithNotesUseCase: GetMarkerWithNotesUseCase
)