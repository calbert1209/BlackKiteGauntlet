package com.calbert.blackkitegauntlet.presentation.data

import kotlinx.coroutines.flow.Flow

class OfflineTidalEventRepository(private val eventDao: TidalEventDao) : TidalEventRepository {
    override suspend fun insertEvent(event: TidalEvent) = eventDao.insert(event)
    override suspend fun updateEvent(event: TidalEvent) = eventDao.update(event)
    override suspend fun deleteEvent(event: TidalEvent) = eventDao.delete(event)
    override fun getEventStream(timestamp: String): Flow<TidalEvent?> = eventDao.getEvent(timestamp)
    override fun getSampleEventStream(): Flow<TidalEvent?> = eventDao.getSampleEvent()
}