package com.example.fishingnotes.feature_notes.domain.use_cases

import com.example.fishingnotes.feature_notes.domain.repository.NoteRepository

class DeleteNotesWithMarkerId(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(markerId: Int) {
        repository.deleteNotesWithMarkerId(markerId)
    }
}