package com.example.fishingnotes.feature_maps.data.data_sourse.MarkerDao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.fishingnotes.feature_maps.domain.model.Marker
import com.example.fishingnotes.feature_maps.domain.model.MarkerWithNotes
import kotlinx.coroutines.flow.Flow

@Dao
interface MarkerDao {

    @Query("SELECT * FROM marker")
    fun getMarkers(): Flow<List<Marker>>

    @Query("SELECT * FROM marker WHERE id = :id")
    suspend fun getMarkerById(id: Int): Marker?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMarker(marker: Marker)

    @Delete
    suspend fun deleteMarker(marker: Marker)

    @Transaction
    @Query("SELECT * FROM marker WHERE id = :id")
    suspend fun getMarkerWithNotes(id: Int): MarkerWithNotes
}