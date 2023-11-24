package com.example.fishingnotes.feature_maps.presentation

import com.google.android.gms.maps.model.LatLng

sealed class MapsEvent {
    data class MapLongClicked(val latLng: LatLng) : MapsEvent()
    object MapClicked : MapsEvent()
}