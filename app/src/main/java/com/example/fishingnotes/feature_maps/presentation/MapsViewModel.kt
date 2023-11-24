package com.example.fishingnotes.feature_maps.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishingnotes.feature_maps.domain.model.InvalidMarkerException
import com.example.fishingnotes.feature_maps.domain.model.Marker
import com.example.fishingnotes.feature_maps.domain.use_cases.MarkerUseCases
import com.example.fishingnotes.feature_notes.domain.use_cases.NoteUseCases
import com.example.fishingnotes.feature_weather_api.domain.use_cases.WeatherUseCases
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val markerUseCases: MarkerUseCases,
    private val noteUseCases: NoteUseCases,
    private val weatherUseCases: WeatherUseCases
) : ViewModel() {

    private val _state = mutableStateOf(MarkersState())
    var state: State<MarkersState> = _state


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private val _markerTitle = mutableStateOf(
        MarkerTextFieldState(
            hint = "Enter title"
        )
    )
    val markerTitle: State<MarkerTextFieldState> = _markerTitle

    private val _latLng = mutableStateOf(
        LatLng(
            0.0,
            0.0
        )
    )
    val latLng: State<LatLng> = _latLng

    private var getMarkersJob: Job? = null

    init {
        getMarkers()
    }

    fun onAlertDialogEvent(event: AlertDialogEvent) {
        when (event) {
            is AlertDialogEvent.OnConfirmationClick -> {
                viewModelScope.launch {
                    _state.value.currentMarker?.let {
                        markerUseCases.deleteMarkerUseCase(
                            it
                        )
                    }
                    _state.value.markerWithNotes?.notes?.onEach { note ->
                        _state.value.currentMarker?.id?.let {
                            noteUseCases.deleteNotesWithMarkerId(it)
                        }
                        note.id?.toInt().let {
                            if (it != null) {
                                weatherUseCases.deleteWeatherDataWithNoteId(it)
                            }
                        }
                    }
                    _eventFlow.emit(UiEvent.OnConfirmationClick)
                }
            }

            is AlertDialogEvent.OnDismissClick -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.OnDismissClick)
                }
            }
        }
    }

    fun onMapEvent(event: MapsEvent) {
        when (event) {
            is MapsEvent.MapLongClicked -> {
                viewModelScope.launch {
                    _latLng.value = event.latLng
                    _markerTitle.value = markerTitle.value.copy(
                        text = ""
                    )
                    _state.value = state.value.copy(
                        savedMarker = false,
                        visible = true,
                        markerWithNotes = null
                    )
                    _eventFlow.emit(UiEvent.MapLongClicked)
                }
            }

            is MapsEvent.MapClicked -> {
                viewModelScope.launch {
                    _markerTitle.value = markerTitle.value.copy(
                        text = ""
                    )
                    _state.value = state.value.copy(
                        visible = false
                    )
                    _eventFlow.emit(UiEvent.MapClicked)
                }
            }
        }
    }

    fun onMarkerEvent(event: MarkerEvent) {
        when (event) {
            is MarkerEvent.MarkerClicked -> {
                viewModelScope.launch {
                    _markerTitle.value = markerTitle.value.copy(
                        text = event.marker.markerTitle,
                    )
                    _state.value = state.value.copy(
                        visible = false,
                        currentMarker = event.marker,
                        savedMarker = true,
                        markerWithNotes = event.marker.id?.let {
                            markerUseCases.getMarkerWithNotesUseCase(it)
                        }
                    )
                    _eventFlow.emit(UiEvent.MarkerClicked)
                }
            }

            is MarkerEvent.UnsavedMarkerClicked -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.MarkerClicked)
                }
            }

            is MarkerEvent.EnteredTitle -> {
                _markerTitle.value = markerTitle.value.copy(
                    text = event.value
                )
            }

            is MarkerEvent.ChangeTitleFocus -> {
                _markerTitle.value = _markerTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && markerTitle.value.text.isBlank()
                )
            }

            is MarkerEvent.SaveMarker -> {
                viewModelScope.launch {
                    try {
                        markerUseCases.addMarkerUseCase(
                            marker = Marker(
                                markerTitle = markerTitle.value.text,
                                latitude = latLng.value.latitude,
                                longitude = latLng.value.longitude
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveMarker)
                    } catch (e: InvalidMarkerException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save the marker"
                            )
                        )
                    }
                }
            }

            is MarkerEvent.DeleteMarker -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.DeleteMarker)
                }
            }
        }
    }

    private fun getMarkers() {
        getMarkersJob?.cancel()
        getMarkersJob = markerUseCases.getMarkersUseCase()
            .onEach { markers ->
                _state.value = state.value.copy(
                    markers = markers
                )
            }.launchIn(viewModelScope)
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveMarker : UiEvent()
        object DeleteMarker : UiEvent()
        object MarkerClicked : UiEvent()
        object MapLongClicked : UiEvent()
        object MapClicked : UiEvent()
        object OnConfirmationClick : UiEvent()
        object OnDismissClick : UiEvent()
        object OnDismissRequest : UiEvent()
    }
}