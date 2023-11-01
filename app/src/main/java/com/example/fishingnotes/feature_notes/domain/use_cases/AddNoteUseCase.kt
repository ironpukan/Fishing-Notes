package com.example.fishingnotes.feature_notes.domain.use_cases

import com.example.fishingnotes.feature_notes.domain.model.InvalidNoteException
import com.example.fishingnotes.feature_notes.domain.model.Note
import com.example.fishingnotes.feature_notes.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty!")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty!")
        }
        repository.addNote(note)
    }
}