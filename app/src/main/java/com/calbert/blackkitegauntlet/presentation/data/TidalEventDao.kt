package com.calbert.blackkitegauntlet.presentation.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TidalEventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(event: TidalEvent)

    @Insert
    fun insertSample(event: TidalEvent)

    @Update
    suspend fun update(event: TidalEvent)

    @Delete
    suspend fun delete(event: TidalEvent)

    @Query("SELECT * from tidal_events WHERE timestamp = :timestamp")
    fun getEvent(timestamp: String): Flow<TidalEvent>

    @Query("SELECT * from tidal_events WHERE timestamp like :date || '%' AND type <> 'hourly' ORDER BY timestamp")
    fun getExtremes(date: String): Flow<List<TidalEvent>>

    @Query("SELECT * from tidal_events WHERE id = 1")
    fun getSampleEvent(): Flow<TidalEvent>
}