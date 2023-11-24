package com.example.fishingnotes.feature_maps.presentation

import com.example.fishingnotes.feature_maps.domain.model.Marker
import com.example.fishingnotes.feature_maps.domain.model.MarkerWithNotes

data class MarkersState(
    val markers: List<Marker> = emptyList(),
    val markerWithNotes: MarkerWithNotes? = null,
    val visible: Boolean = false,
    val savedMarker: Boolean = false,
    val currentMarker: Marker? = null
)