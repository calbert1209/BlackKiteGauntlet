package com.calbert.blackkitegauntlet.presentation.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TidalEvent::class], version = 1, exportSchema = true)
abstract class EventDatabase: RoomDatabase() {
    abstract fun tidalEventDao(): TidalEventDao

    companion object {
        @Volatile
        private var Instance: EventDatabase? = null

        fun getDatabase(context: Context): EventDatabase {
            return Instance ?: synchronized(this) {
                val db = Room.databaseBuilder(context, EventDatabase::class.java, "event_database")
                    .build()
                    .also {
                        Instance = it
                        it.tidalEventDao().insertSample(TidalEvent(1, "hourly", 125, "2024-06-20T00:00:00+0900"))
                    }

                return db;
            }
        }
    }
}