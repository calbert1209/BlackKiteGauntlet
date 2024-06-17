package com.calbert.blackkitegauntlet.presentation.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tidal_events")
data class TidalEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val type: String,
    val level: Int,
    val timestamp: String,
)
