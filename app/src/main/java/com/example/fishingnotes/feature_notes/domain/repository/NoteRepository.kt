package com.example.fishingnotes.feature_notes.domain.repository


import com.example.fishingnotes.feature_notes.domain.model.Note
import com.example.fishingnotes.feature_notes.domain.model.NoteWithWeatherData
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun addNote(note: Note) : Long

    suspend fun deleteNote(note: Note)

    suspend fun deleteNotesWithMarkerId(markerId: Int)

    suspend fun getNoteWithWeatherData(id: Int) : NoteWithWeatherData
}