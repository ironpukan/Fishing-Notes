package com.example.fishingnotes.feature_notes.domain.use_cases

import com.example.fishingnotes.feature_notes.domain.model.Note
import com.example.fishingnotes.feature_notes.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }
}