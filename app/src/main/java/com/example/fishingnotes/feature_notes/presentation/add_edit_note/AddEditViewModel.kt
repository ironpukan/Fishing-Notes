package com.example.fishingnotes.feature_notes.presentation.add_edit_note


import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishingnotes.feature_notes.domain.model.InvalidNoteException
import com.example.fishingnotes.feature_notes.domain.model.Note
import com.example.fishingnotes.feature_notes.domain.use_cases.NoteUseCases
import com.example.fishingnotes.feature_weather_api.data.mappers.toWeatherInfo
import com.example.fishingnotes.feature_weather_api.domain.location.LocationTracker
import com.example.fishingnotes.feature_weather_api.domain.repository.WeatherRepository
import com.example.fishingnotes.feature_weather_api.domain.use_cases.WeatherUseCases
import com.example.fishingnotes.feature_weather_api.presentation.WeatherState
import com.plcoding.weatherapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val weatherUseCases: WeatherUseCases,
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set


    private val _noteTitle = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter title"
        )
    )
    val noteTitle: State<NoteTextFieldState> = _noteTitle


    private val _noteContent = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter some content"
        )
    )
    val noteContent: State<NoteTextFieldState> = _noteContent


    private var markerId: Int? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Long? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            savedStateHandle.get<Int>("markerId")?.let { markerId ->
                this.markerId = markerId
                if (noteId != -1) {
                    viewModelScope.launch {
                        noteUseCases.getNoteUseCase(noteId)?.also { note ->
                            currentNoteId = note.id
                            _noteTitle.value = noteTitle.value.copy(
                                text = note.title,
                                isHintVisible = false
                            )
                            _noteContent.value = noteContent.value.copy(
                                text = note.content,
                                isHintVisible = false
                            )
                        }
                        state = state.copy(
                            weatherInfo = noteUseCases.getNoteWithWeatherData(noteId)
                                .toWeatherInfo()
                        )
                    }
                }
            }
        }
    }


    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        val savedNoteId = noteUseCases.addNoteUseCase(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = LocalDateTime.now(),
                                id = currentNoteId,
                                markerId = markerId
                            )
                        )
                        if (currentNoteId == null) {
                            state.weatherInfo?.weatherDataPerDay?.get(0)?.let { firstDay ->
                                firstDay.forEach {
                                    weatherUseCases.addWeatherDataUseCase(
                                        weatherData = it.copy(
                                            noteId = savedNoteId.toInt()
                                        )
                                    )
                                }
                            }
                        }
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save the note!"
                            )
                        )
                    }
                }
            }
        }
    }

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                when (
                    val result = repository.getWeatherData(
                        location.latitude,
                        location.longitude
                    )
                ) {
                    is Resource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve the location. Make sure to grand permission and enable GPS"
                )
            }
        }
    }
    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}