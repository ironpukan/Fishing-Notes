package com.example.fishingnotes.feature_notes.data.repository

import com.example.fishingnotes.feature_notes.data.data_sourse.NoteDao.NoteDao
import com.example.fishingnotes.feature_notes.domain.model.Note
import com.example.fishingnotes.feature_notes.domain.model.NoteWithWeatherData
import com.example.fishingnotes.feature_notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun addNote(note: Note) : Long {
        return dao.addNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override suspend fun deleteNotesWithMarkerId(markerId: Int) {
        dao.deleteNotesWithMarkerId(markerId)
    }

    override suspend fun getNoteWithWeatherData(id: Int): NoteWithWeatherData {
        return dao.getNoteWithWeatherData(id)
    }
}