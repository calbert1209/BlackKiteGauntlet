package com.calbert.blackkitegauntlet.presentation.data

import android.content.Context

interface AppContainer {
    val eventRepository: TidalEventRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val eventRepository: TidalEventRepository by lazy {
        OfflineTidalEventRepository(EventDatabase.getDatabase(context).tidalEventDao())
    }
}