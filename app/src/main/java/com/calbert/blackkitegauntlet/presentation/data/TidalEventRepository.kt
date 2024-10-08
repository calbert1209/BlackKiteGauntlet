package com.calbert.blackkitegauntlet.presentation.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

interface TidalEventRepository {
    suspend fun insertEvent(event: TidalEvent)
    suspend fun updateEvent(event: TidalEvent)
    suspend fun deleteEvent(event: TidalEvent)
    fun getEventStream(timestamp: String): Flow<TidalEvent?>
    fun getExtremesStream(date: String): Flow<List<TidalEvent>>
    fun getEventsStream(date: String): Flow<List<TidalEvent>>
    fun getSampleEventStream(): Flow<TidalEvent?>
}