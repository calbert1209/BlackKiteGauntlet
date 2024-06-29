package com.calbert.blackkitegauntlet.presentation.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class OfflineTidalEventRepository(private val eventDao: TidalEventDao) : TidalEventRepository {
    override suspend fun insertEvent(event: TidalEvent) = eventDao.insert(event)
    override suspend fun updateEvent(event: TidalEvent) = eventDao.update(event)
    override suspend fun deleteEvent(event: TidalEvent) = eventDao.delete(event)
    override fun getEventStream(timestamp: String): Flow<TidalEvent?> = eventDao.getEvent(timestamp)
    override fun getExtremesStream(date: String): Flow<List<TidalEvent>> = eventDao.getExtremes(date)
    override fun getEventsStream(date: String): Flow<List<TidalEvent>> = eventDao.getEvents(date)
    override fun getSampleEventStream(): Flow<TidalEvent?> = eventDao.getSampleEvent()
}