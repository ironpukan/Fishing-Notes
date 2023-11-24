package com.example.fishingnotes.feature_notes.data.data_sourse.NoteDao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.fishingnotes.feature_notes.domain.model.Note
import com.example.fishingnotes.feature_notes.domain.model.NoteWithWeatherData
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note) : Long

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM note WHERE markerId = :markerId")
    suspend fun deleteNotesWithMarkerId(markerId: Int)

    @Transaction
    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteWithWeatherData(id: Int) : NoteWithWeatherData
}