package com.example.fishingnotes.feature_notes.di

import android.app.Application
import androidx.room.Room
import com.example.fishingnotes.feature_maps.data.repository.MarkerRepositoryImpl
import com.example.fishingnotes.feature_maps.domain.repository.MarkerRepository
import com.example.fishingnotes.feature_maps.domain.use_cases.AddMarkerUseCase
import com.example.fishingnotes.feature_maps.domain.use_cases.DeleteMarkerUseCase
import com.example.fishingnotes.feature_maps.domain.use_cases.GetMarkerUseCase
import com.example.fishingnotes.feature_maps.domain.use_cases.GetMarkerWithNotesUseCase
import com.example.fishingnotes.feature_maps.domain.use_cases.GetMarkersUseCase
import com.example.fishingnotes.feature_maps.domain.use_cases.MarkerUseCases
import com.example.fishingnotes.local_data_sourse.FishingNotesDatabase
import com.example.fishingnotes.feature_notes.data.repository.NoteRepositoryImpl
import com.example.fishingnotes.feature_notes.domain.repository.NoteRepository
import com.example.fishingnotes.feature_notes.domain.use_cases.AddNoteUseCase
import com.example.fishingnotes.feature_notes.domain.use_cases.DeleteNoteUseCase
import com.example.fishingnotes.feature_notes.domain.use_cases.DeleteNotesWithMarkerId
import com.example.fishingnotes.feature_notes.domain.use_cases.GetNoteUseCase
import com.example.fishingnotes.feature_notes.domain.use_cases.GetNoteWithWeatherData
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

    private var INSTANCE: FishingNotesDatabase? = null

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): FishingNotesDatabase {
        synchronized(this) {
            return INSTANCE ?: Room.databaseBuilder(
                app,
                FishingNotesDatabase::class.java,
                FishingNotesDatabase.DATABASE_NAME
            ).build().also {
                INSTANCE = it
            }
        }
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: FishingNotesDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            GetNotesUseCase(repository),
            DeleteNoteUseCase(repository),
            AddNoteUseCase(repository),
            GetNoteUseCase(repository),
            DeleteNotesWithMarkerId(repository),
            GetNoteWithWeatherData(repository)
        )
    }

    @Provides
    @Singleton
    fun provideMarkerRepository(db: FishingNotesDatabase): MarkerRepository {
        return MarkerRepositoryImpl(db.markerDao)
    }

    @Provides
    @Singleton
    fun provideMarkerUseCases(repository: MarkerRepository): MarkerUseCases {
        return MarkerUseCases(
            GetMarkerUseCase(repository),
            AddMarkerUseCase(repository),
            GetMarkersUseCase(repository),
            DeleteMarkerUseCase(repository),
            GetMarkerWithNotesUseCase(repository)
        )
    }
}