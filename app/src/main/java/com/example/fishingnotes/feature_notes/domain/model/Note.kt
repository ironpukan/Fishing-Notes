package com.example.fishingnotes.feature_notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO: Correct timestamp value
@Entity
data class Note(
    val title: String,
    val timestamp: Long,
    val content: String,
    @PrimaryKey val id: Int? = null
)

class InvalidNoteException(message: String) : Exception(message)