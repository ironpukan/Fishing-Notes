package com.example.fishingnotes.feature_notes.domain.use_cases

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val getNoteUseCase: GetNoteUseCase,
    val deleteNotesWithMarkerId: DeleteNotesWithMarkerId,
    val getNoteWithWeatherData: GetNoteWithWeatherData
)