package com.example.fishingnotes.feature_notes.domain.use_cases

import com.example.fishingnotes.feature_notes.domain.model.NoteWithWeatherData
import com.example.fishingnotes.feature_notes.domain.repository.NoteRepository

class GetNoteWithWeatherData(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int) : NoteWithWeatherData {
        return repository.getNoteWithWeatherData(id)
    }
}