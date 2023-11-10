package com.example.fishingnotes.feature_notes.di

import android.app.Application
import androidx.room.Room
import com.example.fishingnotes.feature_notes.data.data_sourse.NoteDatabase
import com.example.fishingnotes.feature_notes.data.repository.NoteRepositoryImpl
import com.example.fishingnotes.feature_notes.domain.repository.NoteRepository
import com.example.fishingnotes.feature_notes.domain.use_cases.AddNoteUseCase
import com.example.fishingnotes.feature_notes.domain.use_cases.DeleteNoteUseCase
import com.example.fishingnotes.feature_notes.domain.use_cases.GetNoteUseCase
import com.example.fishingnotes.feature_notes.domain.use_cases.GetNotesUseCase
import com.example.fishingnotes.feature_notes.domain.use_cases.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            GetNotesUseCase(repository),
            DeleteNoteUseCase(repository),
            AddNoteUseCase(repository),
            GetNoteUseCase(repository)
        )
    }
}