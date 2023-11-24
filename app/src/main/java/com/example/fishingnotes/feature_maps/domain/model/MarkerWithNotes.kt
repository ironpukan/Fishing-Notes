package com.example.fishingnotes.feature_maps.domain.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.fishingnotes.feature_notes.domain.model.Note

data class MarkerWithNotes(
    @Embedded val marker: Marker,
    @Relation(
        parentColumn = "id",
        entityColumn = "markerId"
    )
    val notes: List<Note>
)