package com.calbert.blackkitegauntlet.presentation.data

import android.content.Context
import android.media.metrics.Event
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [TidalEvent::class], version = 1, exportSchema = true)
abstract class EventDatabase: RoomDatabase() {
    abstract fun tidalEventDao(): TidalEventDao

    companion object {
        @Volatile
        private var Instance: EventDatabase? = null

        fun getDatabase(context: Context): EventDatabase {
            return Instance ?: synchronized(this) {
                return Room.databaseBuilder(context, EventDatabase::class.java, "event_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}