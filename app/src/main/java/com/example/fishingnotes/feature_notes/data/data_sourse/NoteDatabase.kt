package com.example.fishingnotes.feature_notes.data.data_sourse

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fishingnotes.feature_notes.data.data_sourse.NoteDao.NoteDao
import com.example.fishingnotes.feature_notes.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1,
)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object{
        const val DATABASE_NAME = "notes_db"
    }
}