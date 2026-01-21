package com.example.meowpoints

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    @Insert
    suspend fun insertEvent(event: CatEvent)
    @Query("SELECT * FROM cat_events ORDER BY timestamp DESC")
    fun getAllEvents(): Flow<List<CatEvent>>
    @Query("SELECT SUM(points)FROM cat_events")
    fun getTotalPoints(): Flow<Int?>

    @Query("DELETE FROM cat_events")
    suspend fun deleteAllEvents()
}