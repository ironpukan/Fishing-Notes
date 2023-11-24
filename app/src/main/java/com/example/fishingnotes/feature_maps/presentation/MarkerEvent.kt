package com.example.fishingnotes.feature_maps.presentation

import androidx.compose.ui.focus.FocusState
import com.example.fishingnotes.feature_maps.domain.model.Marker

sealed class MarkerEvent {

    data class MarkerClicked(val marker: Marker) : MarkerEvent()
    object UnsavedMarkerClicked : MarkerEvent()

    object DeleteMarker : MarkerEvent()

    data class EnteredTitle(val value: String) : MarkerEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : MarkerEvent()

    object SaveMarker : MarkerEvent()
}
